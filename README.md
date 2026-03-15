# MJCompiler Project

This project implements a compiler for the **MicroJava (MJ)** programming language. It follows a standard compilation pipeline including lexical analysis, syntax analysis, semantic analysis, and code generation, targeting the **MicroJava Virtual Machine (MJVM)**.

## ⚙️ Workflow

The compiler functions through a series of stages, orchestrated by the `build.xml` script:

1.  **Lexical Analysis (`Lexer`)** 🔍
    *   **Tool**: JFlex
    *   **Input**: `spec/mjlexer.lex`
    *   **Output**: `src/rs/ac/bg/etf/pp1/Yylex.java`
    *   **Description**: Scans the source code and breaks it into tokens.

2.  **Syntax Analysis (`Parser`)** 🏗️
    *   **Tool**: Java CUP
    *   **Input**: `spec/mjparser.cup`
    *   **Output**: `src/rs/ac/bg/etf/pp1/MJParser.java`, `src/rs/ac/bg/etf/pp1/sym.java`, and AST classes.
    *   **Description**: Parses the token stream according to the grammar and builds an Abstract Syntax Tree (AST).

3.  **Semantic Analysis** 🧠
    *   **Implementation**: `src/rs/ac/bg/etf/pp1/SemanticAnalyzer.java`
    *   **Description**: Traverses the AST to perform type checking, scope resolution, and other semantic validations (e.g., ensuring variables are declared before use, types match in assignments).

4.  **Code Generation** 💾
    *   **Implementation**: `src/rs/ac/bg/etf/pp1/CodeGenerator.java`
    *   **Description**: Traverses the AST to generate bytecode for the MJVM. The output is an `.obj` file.

5.  **Execution** ▶️
    *   **Runtime**: MicroJava Runtime (`mj-runtime-1.1.jar`)
    *   **Description**: The generated `.obj` file is executed by the runtime environment.

## 📂 Important Files

### 🔨 Build and Configuration
*   `build.xml`: The main Ant build script. It defines targets for compiling the project, running tests, and managing the build lifecycle.
*   `config/log4j.xml`: Configuration for the logging framework (Log4j).

### 📜 Specifications
*   `spec/mjlexer.lex`: JFlex specification defining the lexical rules.
*   `spec/mjparser.cup`: CUP specification defining the grammar and parser actions.

### 💻 Source Code
*   `src/rs/ac/bg/etf/pp1/SemanticAnalyzer.java`: Contains the logic for semantic analysis (symbol table management, type checking).
*   `src/rs/ac/bg/etf/pp1/CodeGenerator.java`: Contains the logic for generating MJVM bytecode.
*   `test/rs/ac/bg/etf/pp1/Compiler.java`: Main driver class for the compiler implementation (entry point).

## ⌨️ CLI Usage

> **⚠️ Note**: You must compile the project first! Run `ant compile` (see [Building the Project](#building-the-project) below) to generate the class files before using the CLI.

The compiler can be run directly from the command line using the `Compiler` class (`rs.ac.bg.etf.pp1.Compiler`). It supports three modes of operation based on the number of arguments provided:

*   **0 Arguments** (Default Mode)
    *   **Behavior**: Compiles the default test file `test/program.mj`.
    *   **Output**: Generates `test/program.obj`.
    *   **Command**: `java rs.ac.bg.etf.pp1.Compiler`

*   **1 Argument** (Input Only)
    *   **Behavior**: Compiles the specified source file.
    *   **Output**: Generates an `.obj` file with the same name and in the same directory as the source file.
    *   **Command**: `java rs.ac.bg.etf.pp1.Compiler <source-file.mj>`
    *   **Example**: `java rs.ac.bg.etf.pp1.Compiler test/virtual.mj` (Generates `test/virtual.obj`)

*   **2 Arguments** (Input & Output)
    *   **Behavior**: Compiles the specified source file to a specific output path.
    *   **Output**: Generates the object file at the specified destination.
    *   **Command**: `java rs.ac.bg.etf.pp1.Compiler <source-file.mj> <output-file.obj>`
    *   **Example**: `java rs.ac.bg.etf.pp1.Compiler test/virtual.mj build/custom.obj`

## 🧪 Tests

The `test/` directory contains several `.mj` files used for verifying the compiler's functionality.

*   `test/program.mj`: **Reference Test**. This file tests deep inheritance and polymorphism.
*   `test/test301.mj`: This file tests basic language features, including constants, enums, arrays, and arithmetic operations.
*   `test/test302.mj`: This file tests complex control flow involving boolean logic, function calls, and nested loops with break/continue statements.
*   `test/test303.mj`: This file tests object-oriented concepts, specifically abstract classes, inheritance chains, and polymorphic method dispatch.
*   `test/virtual.mj`: This file tests virtual method invocation, `this` reference usage, and nested method calls on object instances.
*   `test/loop.mj`: This file tests nested loop structures and switch statements, verifying correct behavior of break and continue across scopes.
*   `test/input.txt`: Input data file used when running programs that require standard input (e.g., `read()` calls).

## 🛠️ How to Use

The project uses **Apache Ant** for build and execution automation.

### 🏗️ Building the Project
To generate the lexer/parser and compile the source code:
```powershell
ant compile
```

### ▶️ Running Tests
To compile and run the default test (`test/program.mj`):
```powershell
ant compileInput   # Compiles test/program.mj
ant compileIO      # Compiles to test/program.obj
ant run            # Runs test/program.obj
```

### 🎯 Custom Testing
To test a specific file (e.g., `test/virtual.mj`):
```powershell
ant compileInput -Dsrc.file=test/virtual.mj
ant compileIO -Dsrc.file=test/virtual.mj -Dobj.file=test/virtual.obj
ant run -Dobj.file=test/virtual.obj
```

### 🐞 Debugging & Disassembly
*   **Disassemble**: View the generated bytecode.
    ```powershell
    ant disasm -Dobj.file=test/program.obj
    ```
*   **Debug**: Run with the debug runner.
    ```powershell
    ant debug -Dobj.file=test/program.obj
    ```

### 🧹 Cleaning
To remove generated files:
```powershell
ant delete
```

## 📋 Prerequisites
*   ☕ Java Development Kit (JDK)
*   🐜 Apache Ant
