package database.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BancoQuestoes {

    // Classe de modelo interna para representar cada questão do Cognito
    public static class Questao {
        public String pergunta;
        public List<String> alternativas;
        public int indiceCorreto;
        public String explicacaoAcerto;
        public String explicacaoErro;

        public Questao(String pergunta, List<String> alternativas, int indiceCorreto,
                       String explicacaoAcerto, String explicacaoErro) {
            this.pergunta = pergunta;
            this.alternativas = alternativas;
            this.indiceCorreto = indiceCorreto;
            this.explicacaoAcerto = explicacaoAcerto;
            this.explicacaoErro = explicacaoErro;
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // É AQUI QUE TU VAI ADICIONAR AS 30 QUESTÕES!
    // Eu organizei os métodos abaixo por módulo (dropdown) e por número de livro.
    // é só seguir o modelo dos cases e ir colando os textos das perguntas
    // ─────────────────────────────────────────────────────────────────────────
    
    public static List<Questao> obterQuestoes(String modulo, int idAtividade) {
        if ("Atenção".equalsIgnoreCase(modulo)) {
            return obterQuestoesAtencao(idAtividade);
        } else if ("Lógica".equalsIgnoreCase(modulo)) {
            return obterQuestoesLogica(idAtividade);
        } else if ("Linguagem".equalsIgnoreCase(modulo)) {
            return obterQuestoesLinguagem(idAtividade);
        } else {
            return obterQuestoesGerais(idAtividade); // Módulo padrão (Geral/Memória)
        }
    }

    private static List<Questao> obterQuestoesGerais(int idAtividade) {
        // Álvaro, cada bloco "case" aqui representa um dos 5 livros da nossa trilha de atividades!
        switch (idAtividade) {
            case 1: // Livro 1 (O único ativo que já dá pra jogar no mapa)
                return new ArrayList<>(Arrays.asList(
                    new Questao(
                        "Qual destas opções melhor descreve a Inteligência Artificial no seu cotidiano?",
                        Arrays.asList(
                            "Um robô que substitui os humanos no trabalho",
                            "Um assistente que ajuda a realizar tarefas e encontrar informações mais rápido",
                            "Um vírus de computador que espiona as pessoas",
                            "Um canal de TV interativo"
                        ),
                        1, // Índice da alternativa correta (começa em 0, então 1 é a segunda)
                        "Exatamente! A IA é uma ferramenta que nos ajuda no dia a dia, como quando pedimos receitas ao Google ou usamos o GPS para nos guiar.",
                        "Não se preocupe! A IA não é um robô ou vírus. Ela é como um assistente digital que nos ajuda no dia a dia."
                    ),
                    new Questao(
                        "Para que serve o campo de digitação (prompt) no ChatGPT?",
                        Arrays.asList(
                            "Para fazer ligações telefônicas",
                            "Para assistir vídeos na internet",
                            "Para escrever perguntas ou comandos que você quer que a IA responda",
                            "Para comprar produtos online"
                        ),
                        2,
                        "Perfeito! O prompt é o local onde você escreve o que quer da IA, como se fosse mandar uma mensagem para ela.",
                        "O prompt é onde você escreve suas perguntas para a IA, como se fosse um chat. Você pode pedir receitas ou tirar dúvidas!"
                    )
                ));
            
            case 2: // Livro 2
                return new ArrayList<>(Arrays.asList(
                    new Questao(
                        "cola a pergunta 1 do Livro 2 de Memória aqui!",
                        Arrays.asList("Alternativa Correta (Índice 0)", "Incorreta 1", "Incorreta 2"),
                        0,
                        "Explicação de acerto aqui!",
                        "Explicação de erro aqui!"
                    )
                ));

            case 3: // Livro 3
                return new ArrayList<>(Arrays.asList(
                    new Questao("cola as perguntas do Livro 3", Arrays.asList("Sim", "Não"), 0, "Correto", "Incorreto")
                ));

            case 4: // Livro 4
                return new ArrayList<>(Arrays.asList(
                    new Questao("cola as perguntas do Livro 4", Arrays.asList("Sim", "Não"), 0, "Correto", "Incorreto")
                ));

            case 5: // Livro 5
                return new ArrayList<>(Arrays.asList(
                    new Questao("cola as perguntas do Livro 5", Arrays.asList("Sim", "Não"), 0, "Correto", "Incorreto")
                ));

            default:
                return new ArrayList<>(Arrays.asList(
                    new Questao("Questão padrão Cognito", Arrays.asList("A", "B"), 0, "Acerto", "Erro")
                ));
        }
    }

    private static List<Questao> obterQuestoesAtencao(int idAtividade) {
        // tu cola aqui as suas 5 questões de ATENÇÃO do Módulo de Atenção
        return new ArrayList<>(Arrays.asList(
            new Questao(
                "Questão de Atenção (Modelo)?",
                Arrays.asList("Alternativa 1", "Alternativa 2 (Correta)"),
                1,
                "Acertou a questão de Atenção!",
                "Ops, tente ler com mais atenção!"
            )
        ));
    }

    private static List<Questao> obterQuestoesLogica(int idAtividade) {
        // aqui as suas 5 questões de LÓGICA do Módulo de Lógica
        return new ArrayList<>(Arrays.asList(
            new Questao(
                "Questão de Lógica (Modelo)?",
                Arrays.asList("Resposta Correta", "Resposta Incorreta"),
                0,
                "Ótimo raciocínio lógico!",
                "Raciocínio incorreto, vamos praticar mais!"
            )
        ));
    }

    private static List<Questao> obterQuestoesLinguagem(int idAtividade) {
        // 5 questões de LINGUAGEM do Módulo de Linguagem
        return new ArrayList<>(Arrays.asList(
            new Questao(
                "Questão de Linguagem (Modelo)?",
                Arrays.asList("Correto", "Incorreto"),
                0,
                "Muito bem!",
                "Tente novamente!"
            )
        ));
    }
}