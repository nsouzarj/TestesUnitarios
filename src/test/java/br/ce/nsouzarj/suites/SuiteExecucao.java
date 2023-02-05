package br.ce.nsouzarj.suites;

import br.ce.nsouzarj.servicos.CalculadoraTest;
import br.ce.nsouzarj.servicos.CalculoValorLocacaoTest;
import br.ce.nsouzarj.servicos.LocacaoServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
   CalculadoraTest.class,
        CalculoValorLocacaoTest.class,
        LocacaoServiceTest.class
})
public class SuiteExecucao {



}
