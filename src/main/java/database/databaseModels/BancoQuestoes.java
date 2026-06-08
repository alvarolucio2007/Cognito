package database.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BancoQuestoes {

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

    public static List<Questao> obterQuestoes(String modulo, int idAtividade) {
        if ("Atenção".equalsIgnoreCase(modulo)) {
            return obterQuestoesAtencao(idAtividade);
        } else if ("Lógica".equalsIgnoreCase(modulo)) {
            return obterQuestoesLogica(idAtividade);
        } else if ("Linguagem".equalsIgnoreCase(modulo)) {
            return obterQuestoesLinguagem(idAtividade);
        } else {
            return obterQuestoesGerais(idAtividade); 
        }
    }

    private static List<Questao> obterQuestoesGerais(int idAtividade) {
        switch (idAtividade) {
            case 1: // Livro 1 (Atividade 1) - Inteligência Artificial e Bem-Estar
                return new ArrayList<>(Arrays.asList(
                    new Questao(
                        "Como podemos imaginar a Inteligência Artificial no nosso dia a dia?",
                        Arrays.asList("Como um bicho de sete cabeças", "Como uma ferramenta prática e amiga", "Como algo que só os jovens entendem"),
                        1,
                        "Isso mesmo! A IA é uma ferramenta criada para ser nossa companheira e facilitar as tarefas diárias.",
                        "Quase lá! Lembre-se que a IA não é um mistério, mas sim uma ajuda prática para o seu cotidiano."
                    ),
                    new Questao(
                        "A Inteligência Artificial vai substituir o carinho e o abraço humano?",
                        Arrays.asList("Sim, as máquinas farão tudo", "Talvez no futuro distante", "Não, o afeto humano é insubstituível"),
                        2,
                        "Exatamente! O toque e o afeto humano continuam sendo o centro de tudo e não mudam com a tecnologia.",
                        "Na verdade, a tecnologia serve apenas como apoio. O calor das relações humanas nunca será trocado por máquinas."
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
                    ),
                    new Questao(
                        "Qual o principal benefício de um idoso aprender a usar IA hoje?",
                        Arrays.asList(
                            "Substituir completamente o contato com outras pessoas",
                            "Manter-se conectado com o mundo moderno e ter mais autonomia digital",
                            "Usar o celular apenas para jogos",
                            "Evitar sair de casa para sempre"
                        ),
                        1,
                        "Excelente! Aprender IA ajuda você a se manter conectado, fazer videochamadas, pesquisar sobre saúde e muito mais, com mais independência.",
                        "O maior benefício é a autonomia! Com a IA, você pode pesquisar informações, marcar consultas, falar com familiares e participar do mundo digital com mais facilidade."
                    ),
                    new Questao(
                        "A Inteligência Artificial é como um robô de filme que domina o mundo?",
                        Arrays.asList(
                            "Sim, é muito perigosa",
                            "Não, é apenas um computador treinado para ajudar",
                            "Sim, ela tem vontade própria"
                        ),
                        1,
                        "Correto! Ela é apenas um sistema treinado para reconhecer padrões e nos dar uma mãozinha.",
                        "Pode ficar tranquilo. A realidade é muito mais mansa: a IA é apenas uma ferramenta útil criada por humanos."
                    )
                ));
            
            case 2: // Livro 2 (Atividade 2) - comandos de voz e facilidades
                return new ArrayList<>(Arrays.asList(
                    new Questao(
                        "Qual a vantagem de usar comandos de voz, como falar com a Alexa?",
                        Arrays.asList("Não precisa mais sofrer com letrinhas miúdas no teclado", "É preciso gritar para o aparelho entender", "Serve apenas para ouvir música alta"),
                        0,
                        "Muito bem! Conversar com a tecnologia evita o cansaço visual e o esforço nas mãos.",
                        "Pense na facilidade: em vez de digitar com dificuldade, você pode apenas falar o que precisa."
                    ),
                    new Questao(
                        "O que você pode perguntar para um assistente de voz no seu dia a dia?",
                        Arrays.asList("Apenas perguntas difíceis de escola", "Coisas simples, como a previsão do tempo ou a hora", "Nada, ele não responde perguntas simples"),
                        1,
                        "Isso! Você pode perguntar sobre o clima, as horas ou qualquer dúvida que surja na hora.",
                        "A tecnologia é sua amiga! Pode perguntar coisas simples do cotidiano que ela responde prontamente."
                    ),
                    new Questao(
                        "Como a IA ajuda quem tem dores nas articulações das mãos?",
                        Arrays.asList("Obrigando a pessoa a digitar mais", "Através dos comandos de voz que dispensam o uso dos dedos", "Não ajuda em nada nesse caso"),
                        1,
                        "Exato! Falar em vez de digitar é um alívio enorme para quem tem dificuldades de movimento.",
                        "A voz é o caminho! Com ela, você não precisa forçar suas mãos para enviar mensagens ou fazer buscas."
                    ),
                    new Questao(
                        "Podemos dizer que conversar com a tecnologia é parecido com o quê?",
                        Arrays.asList("Falar com uma visita na sala de casa", "Dar ordens para um soldado", "Falar sozinho no escuro"),
                        0,
                        "Isso mesmo! É um bate-papo tranquilo, como se estivéssemos recebendo alguém para um café.",
                        "A ideia é ser algo natural. É como conversar com um amigo ou visita de forma bem leve."
                    ),
                    new Questao(
                        "Qual desses pilares a IA pode ajudar a organizar no seu lar?",
                        Arrays.asList("Lembrar dos compromissos e datas importantes", "Esconder sua lista de compras", "Nada, ela não tem essa função"),
                        0,
                        "Perfeito! Ela ajuda a manter a memória em dia, lembrando você de datas e eventos felizes.",
                        "A IA funciona como um secretário: ela guarda as datas importantes para você não ter que se preocupar."
                    )
                ));

            case 3: // Livro 3 (Atividade 3) - rotina e independência
                return new ArrayList<>(Arrays.asList(
                    new Questao(
                        "Como o GPS do celular ajuda a visitar a família?",
                        Arrays.asList("Ele escolhe o caminho mais longo", "Ele decora seus caminhos e acha a rota sem trânsito", "Ele desliga o carro sozinho"),
                        1,
                        "Excelente! O GPS aprende seus gostos e te ajuda a chegar mais rápido e com segurança.",
                        "O GPS é um guia: ele encontra o melhor caminho para você aproveitar mais tempo com quem ama."
                    ),
                    new Questao(
                        "Se você tiver poucos ingredientes na geladeira, como a tecnologia ajuda?",
                        Arrays.asList("Ela pede comida cara sozinha", "Ela sugere uma receita deliciosa com o que você já tem", "Ela avisa que você não sabe cozinhar"),
                        1,
                        "Isso! Basta perguntar e ela te dá ideias criativas para aproveitar o que tem em casa.",
                        "A IA é uma ótima mestre-cuca! Ela te ajuda a criar pratos novos com ingredientes simples."
                    ),
                    new Questao(
                        "O que acontece com as sugestões de filmes da IA conforme você a utiliza?",
                        Arrays.asList("As sugestões ficam piores", "Ela para de sugerir coisas", "Ela entende melhor o seu gosto e sugere filmes que você ama"),
                        2,
                        "Exatamente! Quanto mais você usa, mais ela aprende sobre o que te faz feliz no sofá.",
                        "Ela aprende com você! O sistema vai conhecendo seu estilo e recomendando diversão sob medida."
                    ),
                    new Questao(
                        "Qual o principal objetivo da tecnologia na vida de um idoso?",
                        Arrays.asList("Garantir a autonomia e independência por mais tempo", "Deixar a pessoa dependente de robôs", "Substituir as idas ao médico"),
                        0,
                        "Com certeza! A tecnologia serve para que você continue no comando da sua vida com mais segurança.",
                        "O foco é a sua liberdade! Ela traz ferramentas para você fazer suas coisas com mais facilidade e confiança."
                    ),
                    new Questao(
                        "A IA pode ajudar a organizar a lista de supermercado?",
                        Arrays.asList("Sim, para você não perder o papelzinho e nem esquecer nada", "Não, ela não entende nomes de comida", "Sim, mas ela escolhe o que você deve comprar"),
                        0,
                        "Isso aí! Nada de perder o papel da lista; agora tudo fica guardadinho no seu celular.",
                        "A tecnologia organiza seus desejos! Ela ajuda a garantir que nada falte na sua dispensa."
                    )
                ));

            case 4: // Livro 4 (Atividade 4) - saúde e anjo da guarda
                return new ArrayList<>(Arrays.asList(
                    new Questao(
                        "O que são os 'relógios inteligentes' mencionados na aula?",
                        Arrays.asList("Relógios comuns que só mostram as horas", "Verdadeiros anjos da guarda que cuidam da sua saúde", "Aparelhos que complicam a marcação do tempo"),
                        1,
                        "Perfeito! Eles monitoram seu corpo de forma silenciosa e garantem sua segurança.",
                        "Eles são muito especiais! Além das horas, eles cuidam do seu coração e do seu bem-estar."
                    ),
                    new Questao(
                        "Como a tecnologia ajuda com os horários dos remédios?",
                        Arrays.asList("Ela esconde os remédios de você", "Ela avisa a hora exata de tomar cada comprimido", "Ela decide qual remédio você deve tomar sozinha"),
                        1,
                        "Isso mesmo! O alerta avisa o momento certo para você nunca pular uma dose importante.",
                        "Acabou a confusão de horários! O aparelhinho te lembra com carinho o momento de cada medicação."
                    ),
                    new Questao(
                        "Para que serve o sensor de queda no relógio?",
                        Arrays.asList("Para contar quantos passos você deu", "Para avisar a família automaticamente se você sofrer um tropeço forte", "Para avisar que o relógio caiu no chão"),
                        1,
                        "Exato! Se algo acontecer, o relógio pede socorro para seus filhos ou netos na mesma hora.",
                        "É pura segurança! O sensor percebe o impacto e chama ajuda, trazendo paz para você e sua família."
                    ),
                    new Questao(
                        "Como o relógio inteligente acompanha seu coração?",
                        Arrays.asList("Monitorando os batimentos o dia todo de forma silenciosa", "Dando pequenos choques no pulso", "Fazendo muito barulho a cada batida"),
                        0,
                        "Correto! Ele fica ali quietinho, garantindo que seu coração esteja funcionando bem.",
                        "É um cuidado invisível! Ele vigia seu ritmo cardíaco sem atrapalhar em nada sua rotina."
                    ),
                    new Questao(
                        "Qual a sensação que essa tecnologia de saúde traz para a família?",
                        Arrays.asList("Preocupação e medo constante", "Tranquilidade e paz por saber que você está amparado", "Indiferença com o que acontece"),
                        1,
                        "Isso! Saber que você tem uma rede de proteção digital acalma o coração de quem te ama.",
                        "A tecnologia traz união e paz. Seus familiares ficam muito mais tranquilos sabendo que você está seguro."
                    )
                ));

            case 5: // Livro 5 (Atividade 5) - segurança digital
                return new ArrayList<>(Arrays.asList(
                    new Questao(
                        "O que a IA faz ao identificar uma mensagem suspeita ou golpe?",
                        Arrays.asList("Ela ajuda a bloquear fraudes antes de você se chatear", "Ela envia seus dados para o golpista", "Ela ignora o problema"),
                        0,
                        "Perfeito! A tecnologia também funciona como um escudo, protegendo você de pessoas mal-intencionadas.",
                        "Ela te protege! A mesma inteligência que ajuda na receita também vigia mensagens falsas."
                    ),
                    new Questao(
                        "Qual a 'regra de ouro' sobre senhas na internet?",
                        Arrays.asList("Passar para qualquer pessoa que pedir educadamente", "Nunca compartilhar senhas por telefone ou mensagem", "Anotar a senha na capa do celular"),
                        1,
                        "Exato! Suas senhas são suas chaves pessoais e não devem ser entregues a ninguém.",
                        "Segurança em primeiro lugar! Guarde suas senhas com você, não importa quem peça por mensagem."
                    ),
                    new Questao(
                        "Se receber um áudio que parece ser do seu neto pedindo dinheiro, o que fazer?",
                        Arrays.asList("Mandar o dinheiro na hora", "Desconfiar e confirmar de outra forma, pois pode ser um golpe", "Ignorar e nunca mais falar com o neto"),
                        1,
                        "Muito bem! Sempre confirme por uma ligação ou pessoalmente antes de agir, para sua proteção.",
                        "Atenção redobrada! Mesmo que a foto ou voz pareçam reais, sempre verifique antes de passar valores."
                    ),
                    new Questao(
                        "A internet pode ser um lugar seguro para descobertas?",
                        Arrays.asList("Não, é sempre um lugar perigoso", "Sim, desde que você cuide bem das suas 'chaves' e senhas", "Apenas se você não clicar em nada"),
                        1,
                        "Isso mesmo! Com os cuidados certos, o mundo digital é incrível e cheio de coisas boas para aprender.",
                        "O ambiente online é maravilhoso para quem está bem informado e protege seus dados pessoais."
                    ),
                    new Questao(
                        "O que fazer se você tiver um 'friozinho na barriga' ao usar a internet?",
                        Arrays.asList("Desistir de usar para sempre", "Aprender a se proteger e usar a tecnologia como escudo", "Entregar o celular para uma criança usar por você"),
                        1,
                        "Perfeito! O conhecimento é o seu melhor escudo para navegar com tranquilidade e sem medo.",
                        "Sentir receio é normal no começo, mas aprender as dicas de segurança faz esse medo sumir rapidinho."
                    )
                ));

            default: // AVALIAÇÃO GERAL: ESTRELA DO TOPO
                return new ArrayList<>(Arrays.asList(
                    new Questao(
                        "Qual a melhor definição para a Inteligência Artificial apresentada na aula?",
                        Arrays.asList("Um robô que quer substituir os humanos", "Um secretário digital que organiza a vida e cuida da saúde", "Um programa de computador muito difícil de entender", "Um brinquedo moderno para crianças"),
                        1,
                        "Excelente! Você compreendeu que a IA é um apoio prático e protetor para o seu dia a dia.",
                        "Lembre-se da nossa conversa: ela é como um secretário prestativo que está sempre à disposição."
                    ),
                    new Questao(
                        "Sobre a facilidade de uso, o que é verdade?",
                        Arrays.asList("É preciso ser gênio da computação", "É tão simples quanto bater um papo na sala de estar", "Leva anos para aprender o básico", "Apenas jovens conseguem usar sem ajuda"),
                        1,
                        "Isso! A tecnologia moderna se adapta a você, permitindo o uso através da fala e de comandos simples.",
                        "A ideia é a simplicidade! Se você sabe conversar, você já sabe o básico para usar essa tecnologia."
                    ),
                    new Questao(
                        "Quais as três principais funções de saúde dos relógios inteligentes citadas?",
                        Arrays.asList("Jogar videogame, tirar fotos e ver mapas", "Lembrete de remédios, monitoramento cardíaco e sensor de queda", "Medir a altura, pesar a comida e contar o tempo de sono", "Trocar mensagens, ouvir rádio e ver notícias"),
                        1,
                        "Parabéns! Essas três funções são essenciais para garantir um envelhecimento seguro e tranquilo.",
                        "Pense no cuidado: os relógios avisam do remédio, cuidam do coração e pedem ajuda se você cair."
                    ),
                    new Questao(
                        "Por que o comando de voz é considerado 'libertador'?",
                        Arrays.asList("Because the cellular is louder", "Porque dispensa o uso de teclados pequenos e esforço visual", "Porque permite falar com pessoas de outros países", "Porque economiza a bateria do aparelho"),
                        1,
                        "Exato! Ele liberta você da dificuldade de enxergar letrinhas ou de apertar botões pequenos.",
                        "A liberdade vem da fala! Usar a voz torna tudo mais acessível para quem tem qualquer dificuldade física."
                    ),
                    new Questao(
                        "Como a IA ajuda na cozinha e no lazer?",
                        Arrays.asList("Fazendo as compras sozinha sem avisar", "Sugerir receitas com o que tem em casa e recomendar filmes do seu gosto", "Cozinhando a comida para você", "Desligando a TV na hora da novela"),
                        1,
                        "Isso mesmo! Ela é uma companheira que traz ideias para o seu almoço e diversão para o seu descanso.",
                        "Ela atua nas pequenas coisas: dando dicas de culinária e achando aquele filme que é a sua cara."
                    ),
                    new Questao(
                        "Qual atitude garante sua segurança financeira no mundo digital?",
                        Arrays.asList("Contar suas senhas para vizinhos", "Nunca passar senhas ou dados bancários por mensagens ou telefone", "Deixar as senhas salvas em um papel na geladeira", "Sempre acreditar em pedidos de dinheiro por WhatsApp"),
                        1,
                        "Perfeito! Guardar suas senhas a sete chaves é o segredo para uma vida digital sem sustos.",
                        "Proteja-se! Suas senhas são privadas e nunca devem ser compartilhadas, não importa quem peça."
                    ),
                    new Questao(
                        "O que o GPS faz além de mostrar o caminho?",
                        Arrays.asList("Ele decide onde você vai morar", "Ele aprende suas rotas preferidas e evita o trânsito", "Ele avisa quando o combustível acaba", "Ele escolhe quem você deve visitar"),
                        1,
                        "Isso! O GPS se torna inteligente com o tempo, conhecendo os caminhos que você mais gosta de fazer.",
                        "Ele é um assistente de viagens: decora seus caminhos para a casa dos filhos e evita caminhos chatos."
                    ),
                    new Questao(
                        "A IA é comparada a qual profissional para explicar sua utilidade?",
                        Arrays.asList("Um guarda de trânsito", "Um secretário particular 24 horas", "Um professor de matemática", "Um médico cirurgião"),
                        1,
                        "Correto! Ela está sempre de prontidão para organizar seus lembretes e responder suas dúvidas.",
                        "Imagine que você tem um secretário digital: ele nunca tira férias e está sempre pronto para te ajudar."
                    ),
                    new Questao(
                        "Qual o papel do sensor de queda para a autonomia do idoso?",
                        Arrays.asList("Permite que ele corra mais rápido", "Dá confiança para morar e caminhar sozinho, sabendo que o socorro é automático", "Impede que a pessoa caia", "Avisa quando é hora de sentar"),
                        1,
                        "Exatamente! Saber que o socorro será chamado sozinho traz uma liberdade e paz de espírito enormes.",
                        "O sensor traz coragem! Com ele, você se sente mais seguro para realizar suas atividades do dia a dia."
                    ),
                    new Questao(
                        "O que significa dizer que a IA 'reconhece padrões'?",
                        Arrays.asList("Que ela sabe costurar roupas", "Que ela aprende seus gostos e hábitos para facilitar sua vida", "Que ela desenha figuras geométricas", "Que ela só entende coisas repetidas"),
                        1,
                        "Muito bem! Ela percebe o que você gosta e o que você precisa, tornando-se cada vez mais útil para você.",
                        "Aprender com você é o segredo! Ela entende seu jeito e seus horários para te ajudar cada vez melhor."
                    )
                ));
        }
    }

    private static List<Questao> obterQuestoesAtencao(int idAtividade) {
        switch (idAtividade) {
            case 1:
                return Arrays.asList(
                    new Questao("Como podemos definir o assistente digital que mora no celular?", Arrays.asList("Um robô frio e confuso", "Um ajudante educado e muito paciente", "Um sistema que só entende jovens"), 1, "Isso mesmo! Ele é como um amigo sempre pronto para ajudar, sem nunca perder a paciência.", "Na verdade, ele foi feito para ser muito gentil e paciente, explicando as coisas quantas vezes você precisar."),
                    new Questao("O que acontece se você fizer a mesma pergunta várias vezes para o assistente?", Arrays.asList("Ele fica cansado e para de responder", "Ele responde com mau humor", "Ele responde sempre com boa vontade"), 2, "Perfeito! Diferente das pessoas, a tecnologia nunca se cansa de explicar e ajudar.", "Não se preocupe! O assistente digital tem paciência infinita e está sempre de bom humor para tirar suas dúvidas."),
                    new Questao("Para que serve o assistente digital no nosso dia a dia?", Arrays.asList("Para complicar as tarefas", "Para resolver pequenos problemas em segundos", "Para nos obrigar a decorar senhas"), 1, "Exatamente! Ele serve para facilitar a vida e resolver as coisas de um jeito rápido e leve.", "A ideia é simplificar! Ele é um ajudante particular que resolve as dúvidas do cotidiano num piscar de olhos."),
                    new Questao("O assistente digital pode ajudar a escrever uma mensagem de aniversário?", Arrays.asList("Sim, ele cria textos lindos se você pedir", "Não, ele só entende de números", "Sim, mas ele cobra por cada mensagem"), 0, "Com certeza! Se a inspiração sumir, ele ajuda você a escrever palavras carinhosas para quem você ama.", "Ele é muito criativo! Pode pedir ajuda para escrever textos e ele fará isso com muito prazer."),
                    new Questao("Existe 'pergunta boba' na hora de usar a tecnologia?", Arrays.asList("Sim, muitas perguntas não devem ser feitas", "Não, toda dúvida é importante e o assistente responde todas", "Apenas perguntas sobre matemática são aceitas"), 1, "Isso aí! Pode perguntar qualquer coisa, no seu tempo e do seu jeito, sem medo de errar.", "Lembre-se: em um ambiente seguro, não existe pergunta boba. Sinta-se à vontade para tirar qualquer dúvida.")
                );
            case 2:
                return Arrays.asList(
                    new Questao("Se sobrar um pouquinho de frango e brócolis na geladeira, como o assistente ajuda?", Arrays.asList("Ele diz para você jogar fora", "Ele inventa uma receita nova na hora com esses ingredientes", "Ele manda você ir ao restaurante"), 1, "Isso! Ele é um mestre-cuca digital que ajuda você a aproveitar tudo o que tem em casa.", "A tecnologia é muito prática! Ela sugere pratos saudáveis e deliciosos com o que você já tem na geladeira."),
                    new Questao("Como o assistente pode ajudar com a saúde na hora de comer?", Arrays.asList("Sugerindo pratos com menos sal e menos gordura", "Obrigando você a fazer dieta", "Escondendo os doces da despensa"), 0, "Perfeito! Ele dá dicas para você cuidar do corpo sem abrir mão do sabor.", "Ele é um aliado da saúde! Peça dicas de pratos leves e ele te ajudará a manter o bem-estar."),
                    new Questao("O que o assistente pode fazer pelo seu jardim ou pelas suas flores?", Arrays.asList("Nada, ele não gosta de plantas", "Lembrar a hora certa de regar cada tipo de flor", "Mudar as plantas de lugar sozinho"), 1, "Exatamente! Ele ajuda você a manter suas plantinhas sempre bonitas e bem cuidadas.", "Ele entende de jardinagem também! Pode te avisar o momento certo de dar água para cada planta."),
                    new Questao("Qual a vantagem de usar a lista de compras no celular?", Arrays.asList("Ela é organizada por setores e economiza seu tempo", "Ela gasta muito papel", "Ela escolhe o que você deve comprar"), 0, "Isso mesmo! Com a lista organizada, você passeia pelo mercado com muito mais tranquilidade.", "A organização é tudo! O assistente separa os itens por setores para facilitar seu caminho no mercado."),
                    new Questao("O que mais as pessoas gostam de perguntar para os assistentes, segundo a aula?", Arrays.asList("Apenas sobre política", "Dicas para o lar, receitas e recomendações de filmes", "Sobre como consertar carros antigos"), 1, "Correto! As pessoas adoram usar a tecnologia para tornar o lar mais aconchegante e divertido.", "As perguntas mais comuns são as do dia a dia: receitas gostosas e bons filmes para relaxar.")
                );
            case 3:
                return Arrays.asList(
                    new Questao("Antigamente, como fazíamos para tirar uma dúvida difícil?", Arrays.asList("Perguntávamos para o celular", "Abríamos enciclopédias pesadas e procurávamos por horas", "A resposta aparecia na TV"), 1, "Isso mesmo! O esforço era muito maior antes da tecnologia facilitar o acesso à informação.", "Lembra dos livros pesados? Pois é, agora toda aquela informação cabe na palma da sua mão e vem mastigadinha."),
                    new Questao("Como as respostas chegam para você hoje em dia?", Arrays.asList("De um jeito confuso e difícil", "Direto ao ponto, com palavras simples e limpas", "Através de cartas pelo correio"), 1, "Exato! Agora a informação vem clara, como em uma conversa amigável entre amigos.", "O objetivo é a clareza! A tecnologia moderna entrega a resposta pronta para você entender sem esforço."),
                    new Questao("O que significa a informação vir 'mastigadinha'?", Arrays.asList("Que ela é difícil de ler", "Que ela já foi simplificada para você entender rápido", "Que o celular está quebrado"), 1, "Perfeito! Significa que você não precisa mais se perder em labirintos de sites complicados.", "É facilidade pura! A informação chega pronta para ser usada, sem termos técnicos ou confusão."),
                    new Questao("Qual a sensação de mais de 95% das pessoas que usam o assistente?", Arrays.asList("Sentem que a vida digital ficou muito mais leve", "Sentem medo de usar o aparelho", "Sentem que perderam tempo"), 0, "Com certeza! A liberdade de usar a tecnologia sem estresse traz muita alegria no dia a dia.", "A maioria aprova! O uso do assistente traz um alívio enorme e muita autonomia para o cotidiano."),
                    new Questao("O que é melhor: digitar em teclas pequenas ou falar com o celular?", Arrays.asList("Digitar é sempre melhor", "Falar é mais natural e evita o cansaço das mãos", "Nenhuma das duas opções funciona"), 1, "Isso aí! Falar é o jeito mais simples e libertador de se comunicar com o mundo digital.", "Pense no conforto! Usar a voz evita aquele esforço de apertar botõezinhos ou enxergar letras miúdas.")
                );
            case 4:
                return Arrays.asList(
                    new Questao("Qual o primeiro passo para usar o assistente?", Arrays.asList("Comprar um computador novo", "Abrir o aplicativo no seu celular", "Chamar um técnico em informática"), 1, "Exatamente! Tudo começa de forma simples, direto no aparelho que você já tem.", "É mais simples do que parece: basta tocar no ícone do aplicativo para começar a conversa."),
                    new Questao("Para que serve o botão do microfone na tela?", Arrays.asList("Para ouvir música", "Para você falar sua pergunta em vez de digitar", "Para desligar o celular"), 1, "Isso mesmo! Ele funciona como um rádio comunicador: você fala e o assistente te ouve.", "Facilidade na palma da mão! O microfone serve para você dizer o que precisa com sua própria voz."),
                    new Questao("Qual o terceiro passo depois de fazer uma pergunta?", Arrays.asList("Desligar o aparelho", "Ler e aproveitar a resposta que o assistente deu", "Fazer um curso de digitação"), 1, "Perfeito! Agora é só aproveitar a informação e o tempo que você economizou.", "Depois de perguntar, a recompensa vem: a resposta aparece clara para você ler com calma."),
                    new Questao("Por que é importante 'praticar' um pouquinho todo dia?", Arrays.asList("Para o celular não estragar", "Para a confiança aumentar e o medo ir embora", "Para ganhar prêmios da internet"), 1, "Com certeza! Com pequenos testes diários, a tecnologia vira sua melhor amiga rapidinho.", "A prática traz confiança! Cada vez que você usa, percebe que é capaz e que o mundo digital é amigável."),
                    new Questao("O que você pode perguntar para testar o assistente agora?", Arrays.asList("Nada, melhor não mexer", "Uma curiosidade da infância ou a previsão do tempo", "O número de série do aparelho"), 1, "Isso! Brincar com a ferramenta é a melhor forma de aprender e descobrir coisas novas.", "Aproveite para explorar! Pergunte algo divertido e veja como o assistente é prestativo.")
                );
            case 5:
                return Arrays.asList(
                    new Questao("A tecnologia deve mandar na gente ou nos servir?", Arrays.asList("Ela deve mandar em tudo", "Ela está aqui para nos servir e se adaptar ao nosso ritmo", "Nós devemos aprender a língua das máquinas"), 1, "Excelente! As inovações existem para facilitar a nossa vida, no nosso tempo.", "Lembre-se: o senhor da situação é você! A tecnologia é apenas uma ferramenta para te ajudar."),
                    new Questao("O que significa 'empoderamento através da simplicidade'?", Arrays.asList("Que você fica mais forte ao aprender coisas difíceis", "Que você ganha independência ao usar ferramentas simples e úteis", "Que você precisa de muito poder para usar o celular"), 1, "Isso mesmo! Ter autonomia para resolver suas coisas sozinho é uma sensação maravilhosa.", "Simplicidade é poder! Quando você resolve algo com a voz, você se sente mais dono da sua rotina."),
                    new Questao("Qual o sentimento ao perceber que não precisa mais ter medo de apertar o botão errado?", Arrays.asList("Sentimento de liberdade", "Sentimento de tédio", "Sentimento de que nada mudou"), 0, "Perfeito! Perder o medo é o primeiro passo para aproveitar todas as coisas boas da internet.", "A liberdade é o melhor prêmio! Usar o celular com tranquilidade torna o dia muito mais feliz."),
                    new Questao("O assistente digital pode 'salvar o almoço de domingo'?", Arrays.asList("Não, ele não sabe cozinhar", "Sim, dando uma ideia de receita incrível de última hora", "Sim, indo comprar os ingredientes sozinho"), 1, "Exatamente! Ele ajuda você a brilhar na cozinha com ideias criativas e rápidas.", "Ele é um parceiro fiel! Com uma dica de receita, ele transforma ingredientes simples em um banquete."),
                    new Questao("O caminho da tecnologia hoje em dia é cheio de obstáculos ou de atalhos?", Arrays.asList("Cheio de obstáculos e perigos", "Cheio de atalhos gentis e muito úteis", "Um caminho que ninguém consegue seguir"), 1, "Isso aí! A jornada digital agora é muito mais amigável, com caminhos que facilitam sua vida.", "Pense nos atalhos! Em vez de complicação, temos ferramentas gentis que nos levam direto ao que precisamos.")
                );
            case 6: // Avaliação
                return Arrays.asList(
                    new Questao("Como a aula descreve o assistente digital dentro do celular?", Arrays.asList("Como um robô assustador", "Como um assistente extremamente educado e paciente", "Como um vizinho curioso", "Como um livro de matemática"), 1, "Parabéns! Você viu que ele é um ajudante que está sempre lá para te ouvir com carinho.", "A descrição correta é a de um assistente paciente, pronto para te ajudar sem nunca reclamar."),
                    new Questao("O que acontece se você pedir para o assistente explicar algo de um jeito 'mais simples'?", Arrays.asList("Ele para de responder", "Ele explica com palavras ainda mais fáceis", "Ele desliga o celular", "Ele diz que você não entendeu nada"), 1, "Isso! Ele se adapta totalmente a você, mudando o jeito de falar para ficar bem claro.", "O assistente é flexível! Se a primeira explicação foi difícil, ele simplifica até você entender perfeitamente."),
                    new Questao("Qual dessas é uma utilidade prática do assistente na cozinha?", Arrays.asList("Lavar a louça", "Sugerir receitas com o que sobrou na geladeira", "Comer a comida", "Esconder as panelas"), 1, "Muito bem! Ele ajuda a evitar o desperdício e a criar pratos deliciosos com o que você tem.", "Na cozinha, ele é um gênio! Ele usa a criatividade para te dar ideias de receitas rápidas."),
                    new Questao("Além da cozinha, onde mais o assistente pode ajudar no lar?", Arrays.asList("Apenas na sala", "No jardim, lembrando a hora de regar as flores", "Limpando o chão", "Consertando o telhado"), 1, "Correto! Ele cuida até das suas plantas, garantindo que o seu jardim esteja sempre lindo.", "Ele é versátil! Ajuda na cozinha, no jardim e até a organizar sua lista de supermercado."),
                    new Questao("Qual a principal vantagem de usar o comando de voz no celular?", Arrays.asList("Gastar menos bateria", "Falar é mais simples do que lutar com teclas e letras minúsculas", "O celular fica mais bonito", "O volume fica mais alto automaticamente"), 1, "Isso mesmo! A voz é a ferramenta mais natural e confortável que temos para usar a tecnologia.", "Conforto em primeiro lugar! Usar a voz poupa sua visão e não cansa as articulações das mãos."),
                    new Questao("Segundo a aula, como é o ambiente de conversa com a IA?", Arrays.asList("Um lugar perigoso", "Um espaço totalmente seguro, acolhedor e no seu ritmo", "Um lugar onde você é julgado", "Um labirinto confuso"), 1, "Perfeito! É um lugar onde você pode aprender sem pressa e com muita segurança.", "O acolhimento é a chave! A tecnologia moderna respeita o seu tempo e o seu jeito de aprender."),
                    new Questao("Qual a porcentagem de pessoas que acham que a vida digital ficou mais leve com o assistente?", Arrays.asList("Cerca de 10%", "Mais de 95%", "Exatamente 50%", "Apenas 5%"), 1, "Excelente memória! Quase todo mundo que começa a usar percebe como a vida fica mais fácil.", "A grande maioria aprova! Mais de 95% das pessoas sentem um alívio enorme ao usar o assistente."),
                    new Questao("O que o quarto passo ('Praticar') ajuda a combater?", Arrays.asList("A fome", "O estranhamento e a falta de confiança", "O sono", "O excesso de mensagens"), 1, "Isso aí! Praticar um pouquinho todo dia faz a tecnologia parecer algo comum e amigável.", "A prática vence o medo! Com o tempo, o estranhamento some e você se sente um mestre no celular."),
                    new Questao("Como a informação chegava no passado, comparado a hoje?", Arrays.asList("Sempre foi fácil", "Era uma missão em enciclopédias pesadas e sites confusos", "Chegava por telepatia", "Não existia informação"), 1, "Exato! Antigamente era tudo mais difícil e lento. Hoje a resposta vem limpa e direta.", "Pense na evolução! Saímos dos livros pesados para uma conversa rápida e clara no celular."),
                    new Questao("Qual o desejo final da aula para todos os alunos?", Arrays.asList("Que eles comprem mais aparelhos", "Que tenham uma experiência digital maravilhosa e descomplicada", "Que eles parem de usar o celular", "Que eles aprendam a programar computadores"), 1, "Perfeito! O objetivo é que você aproveite o melhor da tecnologia com muita paz e alegria.", "O desejo é o seu bem-estar! Queremos que você use a tecnologia para ser mais independente e feliz.")
                );
            default:
                return new ArrayList<>();
        }
    }

    private static List<Questao> obterQuestoesLogica(int idAtividade) {
        switch (idAtividade) {
            case 1:
                return Arrays.asList(
                    new Questao("O que são os aparelhos 'vestíveis' mencionados na aula?", Arrays.asList("Roupas muito pesadas", "Acessórios pequenos como relógios e anéis que cuidam da saúde", "Aparelhos de ginástica grandes"), 1, "Isso mesmo! São objetos que usamos no corpo de forma natural para monitorar nosso bem-estar.", "Eles são discretos e leves! Pense em um relógio ou um anel que trabalha silenciosamente por você."),
                    new Questao("Como esses aparelhos trabalham no nosso corpo?", Arrays.asList("Fazendo muito barulho", "Em um silêncio absoluto e sem atrapalhar a rotina", "Dando pequenos choques"), 1, "Perfeito! Eles são como ajudantes invisíveis que não causam nenhuma distração no seu dia.", "A discrição é o ponto forte! Eles cuidam de você sem que você nem perceba que estão lá."),
                    new Questao("Além do relógio, qual outro acessório discreto foi citado?", Arrays.asList("Um chapéu inteligente", "Um anel que acompanha o descanso e a temperatura", "Um sapato que fala"), 1, "Exatamente! O anel é tão leve que você nem sente, mas ele vigia sua saúde o tempo todo.", "Existem até anéis inteligentes! Eles são perfeitos para quem quer monitorar o sono com conforto."),
                    new Questao("Qual a grande revolução para quem precisa medir a glicose?", Arrays.asList("Adesivos suaves que colam na pele e evitam picadas de agulha", "Um novo tipo de agulha maior", "Não existe nada novo para isso"), 0, "Isso aí! O conforto desses adesivos acaba com a dor das picadas diárias, trazendo muito alívio.", "Acabou o sofrimento! Agora existem adesivos que medem tudo pela pele, sem precisar de agulhas."),
                    new Questao("Para que servem esses aparelhos, além de mostrar as horas?", Arrays.asList("Para tirar fotos de estranhos", "Para monitorar o bem-estar e deixar a família tranquila", "Para aumentar o volume do rádio"), 1, "Com certeza! Eles trazem paz para você e segurança para todos que te amam.", "O foco é o cuidado! Eles monitoram seu corpo para garantir que você esteja sempre bem.")
                );
            case 2:
                return Arrays.asList(
                    new Questao("Como o relógio ajuda na hora de fazer uma caminhada?", Arrays.asList("Ele impede você de andar", "Ele conta seus passos e motiva você a se movimentar", "Ele toca uma sirene a cada passo"), 1, "Isso mesmo! Ver seu progresso motiva você a manter o corpo ativo e saudável.", "Ele é um parceiro de caminhada! Ele registra seus passos e te ajuda a bater metas de saúde."),
                    new Questao("O que você precisa fazer para mostrar os dados ao médico hoje em dia?", Arrays.asList("Anotar tudo em um caderninho à mão", "Nada, o relógio guarda todas as informações sozinho", "Levar o relógio para o médico consertar"), 1, "Perfeito! O aparelho funciona como um diário de saúde automático e muito preciso.", "Facilidade total! O relógio memoriza sua atividade e seu coração, pronto para ser mostrado ao médico."),
                    new Questao("Onde fica o botão de emergência no sistema vestível?", Arrays.asList("Em cima do telhado", "Super fácil de acessar, às vezes no próprio pulso ou bolso", "Escondido dentro do celular"), 1, "Exato! Em caso de necessidade, o socorro está a apenas um toque de distância.", "Segurança em primeiro lugar! O botão fica sempre à mão para que você nunca se sinta desamparado."),
                    new Questao("O que o relógio faz se perceber que você sofreu uma queda?", Arrays.asList("Ele desliga", "Ele chama socorro automaticamente", "Ele começa a tocar música"), 1, "Isso aí! Se você não conseguir levantar, ele avisa sua família ou a emergência por você.", "Ele é um anjo da guarda! O sensor de queda percebe o acidente e busca ajuda na mesma hora."),
                    new Questao("Antigamente, o que a pessoa tinha que fazer se caísse sozinha?", Arrays.asList("Nada, era fácil", "Tentar levantar com dor para procurar um telefone", "Esperar o despertador tocar"), 1, "Correto! Antes era um estresse enorme, mas agora a tecnologia resolve isso com rapidez.", "Pense na segurança de hoje! O aparelho poupa você do esforço e do medo de ficar sem ajuda após cair.")
                );
            case 3:
                return Arrays.asList(
                    new Questao("Como é o alerta de remédio nos aparelhos modernos?", Arrays.asList("Um barulho muito alto e assustador", "Uma vibração levinha e carinhosa no pulso", "O relógio para de funcionar até você tomar"), 1, "Isso mesmo! É um lembrete gentil que não te assusta, apenas te avisa no momento certo.", "Nada de sustos! O aviso é um toque suave que garante que você nunca esqueça sua medicação."),
                    new Questao("Por que dormir bem é importante, segundo a aula?", Arrays.asList("Para o tempo passar mais rápido", "Para proteger a memória e fortalecer o corpo", "Para não ter que conversar com ninguém"), 1, "Com certeza! O sono de qualidade é um dos melhores remédios para a nossa mente.", "Dormir é essencial! Um bom descanso recarrega as energias e mantém sua memória afiada."),
                    new Questao("O que os aparelhos percebem durante a noite?", Arrays.asList("Se você está roncando alto", "Se o seu sono está leve ou profundo", "Se tem alguém na cozinha"), 1, "Exatamente! Eles entendem seu ritmo biológico para mostrar como foi o seu descanso.", "Eles vigiam seu sono! O aparelho sabe quando você relaxou de verdade e quando o sono foi agitado."),
                    new Questao("O que você recebe ao acordar depois de usar o monitor de sono?", Arrays.asList("Uma conta para pagar", "Um relatório amigável mostrando se seu corpo recarregou as baterias", "Uma lista de tarefas difíceis"), 1, "Perfeito! É maravilhoso entender como nosso corpo funciona sem precisar fazer esforço.", "Ao despertar, você ganha um resumo simples! Ele conta se você descansou o suficiente para o novo dia."),
                    new Questao("As telas desses aparelhos brilham no seu rosto durante a noite?", Arrays.asList("Sim, o tempo o todo", "Não, eles trabalham de forma silenciosa e sem luzes para não atrapalhar", "Sim, mudam de cor a cada hora"), 1, "Isso aí! O conforto é prioridade: eles cuidam de você sem tirar o seu sono.", "Pode dormir tranquilo! O monitoramento é feito no escuro e em silêncio, respeitando seu descanso.")
                );
            case 4:
                return Arrays.asList(
                    new Questao("Qual a função favorita de 95% dos idosos que usam esses aparelhos?", Arrays.asList("Ver vídeos na internet", "Os lembretes de remédios que organizam a vida", "Jogar joguinhos"), 1, "Exatamente! Organizar a medicação traz uma tranquilidade enorme para o dia a dia.", "A campeã é a organização! Saber a hora exata do remédio sem esforço é o que as pessoas mais amam."),
                    new Questao("Quantas pessoas gostam do monitoramento do coração, segundo a aula?", Arrays.asList("Apenas 10%", "Cerca de 85%", "Ninguém gosta"), 1, "Correto! Saber que o coração está sendo vigiado traz muita paz de espírito.", "A aceitação é alta! 85% dos usuários adoram saber que seu ritmo cardíaco está sob cuidado constante."),
                    new Questao("O que os números dizem sobre a confiança dos idosos ao usar tecnologia?", Arrays.asList("Que eles ficam mais medrosos", "Que 90% se sentem mais confiantes para caminhar e morar sozinhos", "Que eles param de sair de casa"), 1, "Com certeza! A tecnologia devolve a independência e a coragem para aproveitar a vida.", "A confiança aumenta muito! Com a proteção digital, nove em cada dez idosos sentem-se seguros em sua rotina."),
                    new Questao("Como a família se sente quando o idoso usa um relógio de saúde?", Arrays.asList("Mais preocupada", "Muito mais tranquila e em paz", "Indiferente"), 1, "Isso mesmo! É um cuidado que envolve todos, trazendo segurança para quem usa e para quem ama.", "Paz para todos! Seus filhos e netos ficam muito mais relaxados sabendo que você está amparado."),
                    new Questao("A tecnologia vestível substitui o médico?", Arrays.asList("Sim, não precisa mais ir ao médico", "Não, ela é uma parceira que ajuda o médico com dados precisos", "Sim, o relógio faz cirurgias"), 1, "Perfeito! Ela é uma ferramenta de apoio que ajuda o doutor a cuidar ainda melhor de você.", "O médico continua essencial! O aparelho apenas fornece informações valiosas para facilitar o tratamento.")
                );
            case 5:
                return Arrays.asList(
                    new Questao("O que se ganha ao adotar essas inovações com leveza?", Arrays.asList("Mais trabalho", "Sua independência de volta e o fim do medo do dia a dia", "Muitas dívidas"), 1, "Excelente! O maior prêmio é poder viver sua vida com liberdade e segurança.", "Independência é a palavra-chave! A tecnologia tira o peso do medo e te devolve a autonomia."),
                    new Questao("A tecnologia vestível é difícil de usar?", Arrays.asList("Sim, exige muitos cursos", "Não, ela é feita para ser natural e se adaptar ao seu corpo", "Apenas cientistas conseguem colocar o relógio"), 1, "Isso aí! Ela foi desenhada para ser simples: basta vestir e deixar que ela cuide do resto.", "É super amigável! O foco desses aparelhos é a simplicidade e o conforto total de quem usa."),
                    new Questao("Qual o efeito de um companheiro tecnológico silencioso na paz de espírito?", Arrays.asList("Causa ansiedade", "Transforma completamente a confiança, trazendo calma", "Não faz diferença nenhuma"), 1, "Perfeito! Saber que você tem uma rede de proteção invisível acalma o coração.", "É transformador! A calma vem de saber que, se algo acontecer, você não estará sozinho."),
                    new Questao("O que a contagem de passos incentiva na melhor idade?", Arrays.asList("Ficar sentado o dia todo", "Manter a saúde em dia através de pequenas metas e caminhadas", "Correr maratonas profissionais"), 1, "Exatamente! Cada passo conta para manter o corpo jovem e cheio de energia.", "Movimento é vida! O relógio te dá aquele empurrãozinho carinhoso para você caminhar um pouco mais."),
                    new Questao("Podemos dizer que a tecnologia é o 'futuro do cuidado'?", Arrays.asList("Sim, pois une carinho, precisão e segurança de um jeito simples", "Não, é apenas um modismo", "Talvez, mas é muito cara"), 0, "Com certeza! É a união perfeita entre o cuidado humano e a inteligência das máquinas.", "É um passo maravilhoso! A tecnologia ajuda a cuidar de quem amamos com muito mais eficiência e afeto.")
                );
            case 6: // Avaliação
                return Arrays.asList(
                    new Questao("Qual a principal diferença entre os aparelhos antigos e os vestíveis modernos?", Arrays.asList("Os antigos eram coloridos", "Os modernos são discretos, silenciosos e automáticos", "Os antigos eram mais precisos", "Não existe diferença nenhuma"), 1, "Parabéns! A grande vantagem moderna é o silêncio e a automação que não atrapalham sua vida.", "Pense na discrição! Hoje os aparelhos cuidam de você sem precisar de cadernos, alarmes altos ou fios."),
                    new Questao("Como o anel inteligente ajuda no monitoramento do sono?", Arrays.asList("Tocando música para ninar", "Medindo a temperatura e o descanso sem precisar de telas", "Acendendo luzes no quarto", "Filmando você dormindo"), 1, "Isso! Ele é tão leve e simples que monitora sua saúde sem que você nem sinta nada no dedo.", "Conforto total! O anel é a prova de que a tecnologia pode ser poderosa e invisível ao mesmo tempo."),
                    new Questao("Qual o benefício dos adesivos de glicose mencionados?", Arrays.asList("Eles brilham no escuro", "Eliminam a necessidade de picadas de agulha dolorosas", "Eles curam a diabetes sozinhos", "Servem apenas para decorar a pele"), 1, "Excelente! Essa é uma das maiores alegrias para quem precisa controlar o açúcar no sangue todos os dias.", "Adeus agulhas! Os adesivos suaves trazem precisão médica com o conforto que você merece."),
                    new Questao("O que o sistema faz se detectar uma queda e você não responder?", Arrays.asList("Ele desliga para economizar bateria", "Ele envia uma mensagem de socorro para seus familiares automaticamente", "Ele começa a piscar luzes coloridas", "Ele toca um alarme alto e para"), 1, "Isso mesmo! Essa função salva vidas e traz uma segurança imensa para quem mora sozinho.", "É um anjo da guarda tecnológico! Ele percebe o impacto e garante que a ajuda chegue rápido até você."),
                    new Questao("Por que os lembretes de remédios no pulso são tão bem aceitos (95%)?", Arrays.asList("Porque são coloridos", "Porque organizam a rotina com uma vibração suave, sem dar sustos", "Porque o relógio é caro", "Porque eles trocam o remédio sozinhos"), 1, "Exato! A suavidade do lembrete ajuda a manter a saúde sem causar estresse ou ansiedade.", "Organização com carinho! O toque no pulso é como um lembrete de um amigo, garantindo sua saúde em dia."),
                    new Questao("O que acontece com os dados de caminhada e batimentos cardíacos?", Arrays.asList("Eles desaparecem depois de uma hora", "Ficam guardados no aparelho para você mostrar ao seu médico", "São enviados para desconhecidos", "Só podem ser vistos por jovens"), 1, "Muito bem! Você agora tem um histórico completo da sua saúde pronto para sua próxima consulta.", "Seu histórico está seguro! O aparelho organiza tudo para que o médico veja como seu corpo se comportou."),
                    new Questao("Qual a sensação relatada por 90% dos idosos que usam essa tecnologia?", Arrays.asList("Confusão e medo", "Maior confiança e segurança para realizar suas tarefas diárias", "Cansaço extremo", "Desejo de não usar mais o celular"), 1, "Com certeza! A proteção digital devolve a alegria de fazer as coisas com autonomia.", "Confiança é tudo! Saber que você está protegido te dá coragem para caminhar, cozinhar e viver bem."),
                    new Questao("Como a aula define a relação entre tecnologia e independência?", Arrays.asList("A tecnologia tira a independência", "A tecnologia devolve a independência e acaba com o medo do dia a dia", "Independência não tem relação com tecnologia", "Apenas robôs são independentes"), 1, "Isso aí! As ferramentas modernas servem para que você continue sendo o dono da sua própria vida.", "Tecnologia é liberdade! Ela traz os recursos necessários para você viver por conta própria com segurança."),
                    new Questao("Qual o papel do monitoramento cardíaco silencioso?", Arrays.asList("Dar sustos no usuário", "Vigiar o coração o tempo todo para garantir o bem-estar", "Fazer o coração bater mais rápido", "Contar quantas músicas você ouviu"), 1, "Perfeito! Ele trabalha quietinho, garantindo que qualquer alteração seja notada a tempo.", "Cuidado invisível! O relógio ou anel vigia seu ritmo cardíaco para que você não precise se preocupar com isso."),
                    new Questao("O que significa dizer que o conforto 'casa' com a precisão médica?", Arrays.asList("Que os médicos estão se casando", "Que os aparelhos são bonitos, mas não funcionam", "Que é possível ter dados médicos exatos usando algo muito confortável", "Que os hospitais agora são em casas"), 2, "Parabéns! Você entendeu o segredo: cuidar da saúde não precisa ser desconfortável ou doloroso.", "O melhor dos dois mundos! Você tem a segurança de um hospital com o conforto de estar na sua própria casa.")
                );
            default:
                return new ArrayList<>();
        }
    }

    private static List<Questao> obterQuestoesLinguagem(int idAtividade) {
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