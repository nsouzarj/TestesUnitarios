package br.ce.nsouzarj.matchers;

import br.ce.nsouzarj.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataDiferencaDiasMatcher extends TypeSafeMatcher<Date> {
    private Integer qtdDias;

    public DataDiferencaDiasMatcher(Integer qtdDias) {
        this.qtdDias = qtdDias;
    }

    public void describeTo(Description desc) {
        // TODO Auto-generated method stub
        Date dataesperada= DataUtils.obterDataComDiferencaDias(qtdDias);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        desc.appendText(dateFormat.format(dataesperada)) ;
    }

    @Override
    protected boolean matchesSafely(Date data) {
        return DataUtils.isMesmaData(data, DataUtils.obterDataComDiferencaDias(qtdDias));
    }

}
