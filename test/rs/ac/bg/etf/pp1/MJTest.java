package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.Obj;
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
			File sourceCode = new File("test/test303.mj");
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

			// inicijalizacija tabele simbola
			Tab.init();
			Obj bool = Tab.insert(Obj.Type, "bool", new Struct(Struct.Bool));
			bool.setAdr(-1);
			bool.setLevel(-1);

			// semanticka analiza
			SemanticAnalyzer analyzer = new SemanticAnalyzer();
			prog.traverseBottomUp(analyzer);

			// ispis tabele simbola
			Tab.dump();

			if (!p.errorDetected && analyzer.passed()) log.info("Parsiranje uspesno");
			else log.error("Parsiranje NEuspesno");
		}
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}

	}


}
