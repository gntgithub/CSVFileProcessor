package com.gnt.camel.routes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CsvFileProcessor extends RouteBuilder {
	
	final String regex = "\\\"[^\\\"]*\\\"";
	
	final Pattern pattern = Pattern.compile(regex);

	@Override
	public void configure() throws Exception {
		from("file://csv/?noop=true&idempotentKey=${file:name}-${file:size}")
		
		// working code
//		.split(body().tokenize("\"{1}+\n",1,true))
		
//		.split(body().tokenize("[\"\n]{1}+\\z",1,true))
		.split(body().tokenize("[\"\n\"]{1}+\n",1,true))
        .streaming().parallelProcessing()
        .process(new Processor() {
            public void process(Exchange msg) {
                String line = msg.getIn().getBody(String.class);
                checkIfLineContainsNewLines(line);
            }

			private void checkIfLineContainsNewLines(String line) {
				line = line.trim().concat("\"");
				if(line.contains("\n")) {
					line = line.replaceAll("\n", "");
				}
				System.out.println(line);
				
				
			}
        }).end();
		
		from("direct:test")
		.log("direct message reached");

	}

}
