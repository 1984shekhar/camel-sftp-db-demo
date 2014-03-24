package org.jboss.fuse;

import org.apache.camel.PropertyInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.idempotent.MemoryIdempotentRepository;

/**
 * Camel Demo
 */
public class MyRouteBuilder extends RouteBuilder {

    public void configure() {

        // Send Files to a directory scanned
        // There are used for SEND PROCESS
        from("file:src/data/send?noop=true")
          .to("file:target/messages/send");

        // Read files to be send to the SFTP Server
        // Insert Record in a DB
        from("file:target/messages/send")
          .onException(Exception.class)
                .handled(true)
                .log("Exception occurred : ${exception.stacktrace}")
                .end()
          .log("File to be send : ${file:name}")
          // Extract Id, Code from XML message
          .setHeader("id").xpath("/request/id")
          .setHeader("code").xpath("/request/code")
           // Create Record in the DB
          .beanRef("requestService", "create")
          // Send file to SFTP Server
          .to("sftp://{{username}}@localhost:22{{sftp_send_path}}?password={{password}}");

        // Send Files to a directory scanned
        // They are used for RESPONSE process
        // We delay to 5s the responses
        from("file:src/data/response?noop=true")
            .delayer(5000)
            .to("file:{{target_message_response}}");

        // Read Response received
        from("sftp://{{username}}@localhost:22{{sftp_receive_path}}?password={{password}}&delete=true")
/*                .idempotentConsumer(simple("${file:name}"),
                        MemoryIdempotentRepository.memoryIdempotentRepository(200))*/
                // Extract Id from XML message
                .setHeader("id").xpath("/request/id")
                // Update Record in the DB with the response
                .beanRef("requestService", "update")
                .log(">> Response received  ${file:name}");

    }

}
