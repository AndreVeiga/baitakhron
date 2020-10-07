package com.aulas.mobile.trabalho.common.validator;

public interface IValidator<P> {
    String isCriacaoValida(P p);
    String isAlteracaoValida(P p);
}
