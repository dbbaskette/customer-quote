# PROJECT.md

## 📘 Project Overview

This project is for a customer demo of Spring Application Advisor.  I don't need it to be fully functional, it just needs to look and behave like it is.  It is the backend appliction that takes user input in the form of JSON and creates an insurance policy quote.  Since we are demoing Spring Application Advisor the idea is that this should use an older JVM, Spring Boot 2.4 or something around there and other compatible older libraries.  That way we can use SAA to update it as the demo.

## 🧩 Functional Requirements

Should take JSON Input to its API and return JSON output of a quote.  Make the code verbose so that it at least looks like a fully functional app.  You can implement as much real code as you want... it needs to look real, so if it really works...thats great


## 🔧 Tech Stack

- Git
- Maven

## 🏛 Architectural Constraints

- Follow KISS principles
- Follow official Spring AI documentation closely
- No magic or reflection-based behavior
- Keep CLI minimal and composable
- Git project with `.gitignore` to exclude sensitive files

## 🔗 References

- [Spring AI Docs](https://docs.spring.io/spring-ai/reference/getting-started.html)
- [Spring AI MCP Overview](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-overview.html)
- [MCP Client Boot Starter](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-client-boot-starter-docs.html)
- [MCP Helpers](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-helpers.html)
- [Spring AI Examples](https://github.com/spring-projects/spring-ai-examples/tree/main/model-context-protocol/client-starter/starter-default-client)
- [Awesome Spring AI](https://github.com/spring-ai-community/awesome-spring-ai)

## 🧪 Development Status

- [ ] CLI Scaffolding
- [ ] Tool Listing
- [ ] Tool Description
- [ ] Direct Execution
- [ ] MCP Integration

## 🆕 Updated Functional Requirements

- The quote response must include:
  - Customer details (from input)
  - Vehicle details (from input)
  - Coverages: liability, collision, comprehensive
- Coverage calculation business rules will be defined in code for demo purposes.