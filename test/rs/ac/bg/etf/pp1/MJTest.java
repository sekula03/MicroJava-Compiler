package rs.ac.bg.etf.pp1;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;

public class MJTest {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}

	public static void main(String[] args) throws Exception {

		Logger log = Logger.getLogger(MJTest.class);

		Reader br = null;
		try {
			File sourceCode = new File("test/program.mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());

			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);

			// parsiranje
			MJParser p = new MJParser(lexer);
			Symbol s = p.parse();
			Program prog = (Program)(s.value);

			// ispis sintaksnog stabla
			log.info(prog.toString(""));
			log.info("============================================================");

			// semanticka analiza
			SemanticAnalyzer analyzer = new SemanticAnalyzer();
			prog.traverseBottomUp(analyzer);

			// ispis tabele simbola
			tsdump();

			if (!p.errorDetected && analyzer.passed()) {

				// generisanje koda
				File objFile = new File("test/program.obj");
				if (objFile.exists()) objFile.delete();

				CodeGenerator codeGenerator = new CodeGenerator();
				prog.traverseBottomUp(codeGenerator);
				Code.dataSize = analyzer.getGlobalVariables();
				Code.mainPc = codeGenerator.getMainPC();
				Code.write(Files.newOutputStream(objFile.toPath()));

				log.info("Generisanje koda uspesno");
			}
			else log.error("Parsiranje NEuspesno");
		}
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}
	}

	private static final HashMap<Struct, String> typeNames = new HashMap<>();

	public static void tsdump() {
		System.out.println("=====================SYMBOL TABLE DUMP=========================");
		Scope universe = Tab.currentScope();
		Obj program = Tab.noObj;
		for (Obj obj: universe.values()) {
			if (obj.getKind() == Obj.Prog) program = obj;
		}
		typeNames.put(Tab.noType, "void");
		System.out.println("Type void");
		typeNames.put(Tab.intType, "int");
		System.out.println("Type int");
		typeNames.put(Tab.charType, "char");
		System.out.println("Type char");
		typeNames.put(universe.findSymbol("bool").getType(), "bool");
		System.out.println("Type bool");
		for (Obj obj: program.getLocalSymbols()) {
			if (obj.getKind() == Obj.Type) {
				typeNames.put(obj.getType(), obj.getName());
				dumpObj(obj, 0);
			}
		}
		System.out.println("Program " + program.getName());
		dumpObj(universe.findSymbol("eol"), 1);
		dumpObj(universe.findSymbol("null"), 1);
		for (Obj obj: program.getLocalSymbols()) {
			if (obj.getKind() == Obj.Con) dumpObj(obj, 1);
		}
		for (Obj obj: program.getLocalSymbols()) {
			if (obj.getKind() == Obj.Var) dumpObj(obj, 1);
		}
		dumpObj(Tab.chrObj, 1);
		dumpObj(Tab.ordObj, 1);
		dumpObj(Tab.lenObj, 1);
		for (Obj obj: program.getLocalSymbols()) {
			if (obj.getKind() == Obj.Meth) dumpObj(obj, 1);
		}
		System.out.println("===============================================================");
	}

	private static void dumpObj(Obj obj, int level) {
		for (int i = 0; i < level; i++) System.out.print("\t");
		String kindName = "";
		Struct type = obj.getType();
		switch (obj.getKind()) {
			case Obj.Con:  kindName = "Const"; break;
			case Obj.Var:  kindName = (obj.getFpPos() > 0) ? "Param" : "Var"; break;
			case Obj.Type: {
				kindName = "Type";
				if (type.getKind() == Struct.Enum) kindName += " enum";
				else if (type.getKind() == Struct.Class || type.getKind() == Struct.Interface) kindName += " class";
				break;
			}
			case Obj.Meth: kindName = "Method"; break;
			case Obj.Fld:  kindName = "Field"; break;
		}
		String typeName = type.getKind() == Struct.Array ? typeNames.get(type.getElemType()) + "[]" :
				type.getKind() == Struct.Enum ? typeNames.get(Tab.intType) : typeNames.get(type);
		System.out.print(kindName + " " + obj.getName());
		if (obj.getKind() != Obj.Type) {
			if (obj.getKind() == Obj.Con)
				System.out.print("(" + obj.getAdr() + ")");
			System.out.print(":" + typeName);
		}
		System.out.println();
		if (obj.getKind() == Obj.Type)
			for (Obj o: type.getMembers()) dumpObj(o, level + 1);
		else if (obj.getKind() == Obj.Meth)
			for (Obj o: obj.getLocalSymbols()) dumpObj(o, level + 1);
	}
}
