package pl.edu.pja.s13868.miniproject1.domain.persistence;

import java.util.List;


/**
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public class DataHandler<T> {
    public void onError() {
    }

    public void onSuccess(List<T> pDataList) {
    }

    public void onSuccess(T pObject) {
    }
}
