package tech.dock.desafio.api.builder;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;

/**
 * Classe base para criação de construtores de entidades para testes
 * automatizados.
 *
 * @param <E> parâmetro
 */
public abstract class ConstrutorDeEntidade<E, T> {

    private CustomizacaoEntidade<E> customizacao;

    /**
     * Constroi a entidade, realizando as custimizações, caso necessário e
     * persistindo a mesma no banco
     *
     * @return entidade construída
     * @throws ParseException Exceção a ser lançada
     */
    public Mono<E> construir() throws ParseException {
        final E entidade = construirEntidade();
        if (isCustomizado()) {
            customizacao.executar(entidade);
        }
        return persistir(entidade);
    }

    /**
     * Este método permite a customização dos atributos da entidade antes da
     * persistência
     *
     * @param customizacao customizacao
     * @return entidade customizada
     */
    public ConstrutorDeEntidade<E, T> customizar(CustomizacaoEntidade<E> customizacao) {
        this.customizacao = customizacao;
        return this;
    }

    /**
     * Este método deve retornar uma instância da entidade inicializada com os
     * dados padrão para todos os testes.
     *
     * @return entidade construída
     * @throws ParseException Exceção a ser lançada
     */
    public abstract E construirEntidade() throws ParseException;

    /**
     * Este método deve persistir e retornar a entidade recebida no parametro
     * <b>entidade</b>
     *
     * @param entidade entidade
     * @return entidade persistida
     */
    public abstract Mono<E> persistir(E entidade);

    /**
     * Este método deve persistir e retornar a entidade recebida no parametro
     * <b>entidade</b>
     *
     * @return entidade persistida
     */
    protected abstract Flux<E> obterTodos();

    /**
     * Este método deve persistir e retornar a entidade recebida no parametro
     * <b>entidade</b>
     *
     * @param id id
     * @return entidade persistida
     */
    protected abstract Mono<E> obterPorId(T id);

    /**
     * Is customizado boolean.
     *
     * @return boolean
     */
    public boolean isCustomizado() {
        return this.customizacao != null;
    }

    /**
     * @param customizacao Atribui o valor do parâmetro no atributo customizacao
     */
    public void setCustomizacao(CustomizacaoEntidade<E> customizacao) {
        this.customizacao = customizacao;
    }

}