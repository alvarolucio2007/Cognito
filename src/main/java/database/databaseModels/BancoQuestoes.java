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
                Arrays.asList("Como um bicho de sete cabeças", "Como uma ferramenta prática e amiga",
                    "Como algo que só os jovens entendem"),
                1,
                "Isso mesmo! A IA é uma ferramenta criada para ser nossa companheira e facilitar as tarefas diárias.",
                "Quase lá! Lembre-se que a IA não é um mistério, mas sim uma ajuda prática para o seu cotidiano."),
            new Questao(
                "A Inteligência Artificial vai substituir o carinho e o abraço humano?",
                Arrays.asList("Sim, as máquinas farão tudo", "Talvez no futuro distante",
                    "Não, o afeto humano é insubstituível"),
                2,
                "Exatamente! O toque e o afeto humano continuam sendo o centro de tudo e não mudam com a tecnologia.",
                "Na verdade, a tecnologia serve apenas como apoio. O calor das relações humanas nunca será trocado por máquinas."),
            new Questao(
                "Para que serve o campo de digitação (prompt) no ChatGPT?",
                Arrays.asList(
                    "Para fazer ligações telefônicas",
                    "Para assistir vídeos na internet",
                    "Para escrever perguntas ou comandos que você quer que a IA responda",
                    "Para comprar produtos online"),
                2,
                "Perfeito! O prompt é o local onde você escreve o que quer da IA, como se fosse mandar uma mensagem para ela.",
                "O prompt é onde você escreve suas perguntas para a IA, como se fosse um chat. Você pode pedir receitas ou tirar dúvidas!"),
            new Questao(
                "Qual o principal benefício de um idoso aprender a usar IA hoje?",
                Arrays.asList(
                    "Substituir completamente o contato com outras pessoas",
                    "Manter-se conectado com o mundo moderno e ter mais autonomia digital",
                    "Usar o celular apenas para jogos",
                    "Evitar sair de casa para sempre"),
                1,
                "Excelente! Aprender IA ajuda você a se manter conectado, fazer videochamadas, pesquisar sobre saúde e muito mais, com mais independência.",
                "O maior benefício é a autonomia! Com a IA, você pode pesquisar informações, marcar consultas, falar com familiares e participar do mundo digital com mais facilidade."),
            new Questao(
                "A Inteligência Artificial é como um robô de filme que domina o mundo?",
                Arrays.asList(
                    "Sim, é muito perigosa",
                    "Não, é apenas um computador treinado para ajudar",
                    "Sim, ela tem vontade própria"),
                1,
                "Correto! Ela é apenas um sistema treinado para reconhecer padrões e nos dar uma mãozinha.",
                "Pode ficar tranquilo. A realidade é muito mais mansa: a IA é apenas uma ferramenta útil criada por humanos.")));

      case 2: // Livro 2 (Atividade 2) - comandos de voz e facilidades
        return new ArrayList<>(Arrays.asList(
            new Questao(
                "Qual a vantagem de usar comandos de voz, como falar com a Alexa?",
                Arrays.asList("Não precisa mais sofrer com letrinhas miúdas no teclado",
                    "É preciso gritar para o aparelho entender", "Serve apenas para ouvir música alta"),
                0,
                "Muito bem! Conversar com a tecnologia evita o cansaço visual e o esforço nas mãos.",
                "Pense na facilidade: em vez de digitar com dificuldade, você pode apenas falar o que precisa."),
            new Questao(
                "O que você pode perguntar para um assistente de voz no seu dia a dia?",
                Arrays.asList("Apenas perguntas difíceis de escola",
                    "Coisas simples, como a previsão do tempo ou a hora", "Nada, ele não responde perguntas simples"),
                1,
                "Isso! Você pode perguntar sobre o clima, as horas ou qualquer dúvida que surja na hora.",
                "A tecnologia é sua amiga! Pode perguntar coisas simples do cotidiano que ela responde prontamente."),
            new Questao(
                "Como a IA ajuda quem tem dores nas articulações das mãos?",
                Arrays.asList("Obrigando a pessoa a digitar mais",
                    "Através dos comandos de voz que dispensam o uso dos dedos", "Não ajuda em nada nesse caso"),
                1,
                "Exato! Falar em vez de digitar é um alívio enorme para quem tem dificuldades de movimento.",
                "A voz é o caminho! Com ela, você não precisa forçar suas mãos para enviar mensagens ou fazer buscas."),
            new Questao(
                "Podemos dizer que conversar com a tecnologia é parecido com o quê?",
                Arrays.asList("Falar com uma visita na sala de casa", "Dar ordens para um soldado",
                    "Falar sozinho no escuro"),
                0,
                "Isso mesmo! É um bate-papo tranquilo, como se estivéssemos recebendo alguém para um café.",
                "A ideia é ser algo natural. É como conversar com um amigo ou visita de forma bem leve."),
            new Questao(
                "Qual desses pilares a IA pode ajudar a organizar no seu lar?",
                Arrays.asList("Lembrar dos compromissos e datas importantes", "Esconder sua lista de compras",
                    "Nada, ela não tem essa função"),
                0,
                "Perfeito! Ela ajuda a manter a memória em dia, lembrando você de datas e eventos felizes.",
                "A IA funciona como um secretário: ela guarda as datas importantes para você não ter que se preocupar.")));

      case 3: // Livro 3 (Atividade 3) - rotina e independência
        return new ArrayList<>(Arrays.asList(
            new Questao(
                "Como o GPS do celular ajuda a visitar a família?",
                Arrays.asList("Ele escolhe o caminho mais longo", "Ele decora seus caminhos e acha a rota sem trânsito",
                    "Ele desliga o carro sozinho"),
                1,
                "Excelente! O GPS aprende seus gostos e te ajuda a chegar mais rápido e com segurança.",
                "O GPS é um guia: ele encontra o melhor caminho para você aproveitar mais tempo com quem ama."),
            new Questao(
                "Se você tiver poucos ingredientes na geladeira, como a tecnologia ajuda?",
                Arrays.asList("Ela pede comida cara sozinha", "Ela sugere uma receita deliciosa com o que você já tem",
                    "Ela avisa que você não sabe cozinhar"),
                1,
                "Isso! Basta perguntar e ela te dá ideias criativas para aproveitar o que tem em casa.",
                "A IA é uma ótima mestre-cuca! Ela te ajuda a criar pratos novos com ingredientes simples."),
            new Questao(
                "O que acontece com as sugestões de filmes da IA conforme você a utiliza?",
                Arrays.asList("As sugestões ficam piores", "Ela para de sugerir coisas",
                    "Ela entende melhor o seu gosto e sugere filmes que você ama"),
                2,
                "Exatamente! Quanto mais você usa, mais ela aprende sobre o que te faz feliz no sofá.",
                "Ela aprende com você! O sistema vai conhecendo seu estilo e recomendando diversão sob medida."),
            new Questao(
                "Qual o principal objetivo da tecnologia na vida de um idoso?",
                Arrays.asList("Garantir a autonomia e independência por mais tempo",
                    "Deixar a pessoa dependente de robôs", "Substituir as idas ao médico"),
                0,
                "Com certeza! A tecnologia serve para que você continue no comando da sua vida com mais segurança.",
                "O foco é a sua liberdade! Ela traz ferramentas para você fazer suas coisas com mais facilidade e confiança."),
            new Questao(
                "A IA pode ajudar a organizar a lista de supermercado?",
                Arrays.asList("Sim, para você não perder o papelzinho e nem esquecer nada",
                    "Não, ela não entende nomes de comida", "Sim, mas ela escolhe o que você deve comprar"),
                0,
                "Isso aí! Nada de perder o papel da lista; agora tudo fica guardadinho no seu celular.",
                "A tecnologia organiza seus desejos! Ela ajuda a garantir que nada falte na sua dispensa.")));

      case 4: // Livro 4 (Atividade 4) - saúde e anjo da guarda
        return new ArrayList<>(Arrays.asList(
            new Questao(
                "O que são os 'relógios inteligentes' mencionados na aula?",
                Arrays.asList("Relógios comuns que só mostram as horas",
                    "Verdadeiros anjos da guarda que cuidam da sua saúde",
                    "Aparelhos que complicam a marcação do tempo"),
                1,
                "Perfeito! Eles monitoram seu corpo de forma silenciosa e garantem sua segurança.",
                "Eles são muito especiais! Além das horas, eles cuidam do seu coração e do seu bem-estar."),
            new Questao(
                "Como a tecnologia ajuda com os horários dos remédios?",
                Arrays.asList("Ela esconde os remédios de você", "Ela avisa a hora exata de tomar cada comprimido",
                    "Ela decide qual remédio você deve tomar sozinha"),
                1,
                "Isso mesmo! O alerta avisa o momento certo para você nunca pular uma dose importante.",
                "Acabou a confusão de horários! O aparelhinho te lembra com carinho o momento de cada medicação."),
            new Questao(
                "Para que serve o sensor de queda no relógio?",
                Arrays.asList("Para contar quantos passos você deu",
                    "Para avisar a família automaticamente se você sofrer um tropeço forte",
                    "Para avisar que o relógio caiu no chão"),
                1,
                "Exato! Se algo acontecer, o relógio pede socorro para seus filhos ou netos na mesma hora.",
                "É pura segurança! O sensor percebe o impacto e chama ajuda, trazendo paz para você e sua família."),
            new Questao(
                "Como o relógio inteligente acompanha seu coração?",
                Arrays.asList("Monitorando os batimentos o dia todo de forma silenciosa",
                    "Dando pequenos choques no pulso", "Fazendo muito barulho a cada batida"),
                0,
                "Correto! Ele fica ali quietinho, garantindo que seu coração esteja funcionando bem.",
                "É um cuidado invisível! Ele vigia seu ritmo cardíaco sem atrapalhar em nada sua rotina."),
            new Questao(
                "Qual a sensação que essa tecnologia de saúde traz para a família?",
                Arrays.asList("Preocupação e medo constante", "Tranquilidade e paz por saber que você está amparado",
                    "Indiferença com o que acontece"),
                1,
                "Isso! Saber que você tem uma rede de proteção digital acalma o coração de quem te ama.",
                "A tecnologia traz união e paz. Seus familiares ficam muito mais tranquilos sabendo que você está seguro.")));

      case 5: // Livro 5 (Atividade 5) - segurança digital
        return new ArrayList<>(Arrays.asList(
            new Questao(
                "O que a IA faz ao identificar uma mensagem suspeita ou golpe?",
                Arrays.asList("Ela ajuda a bloquear fraudes antes de você se chatear",
                    "Ela envia seus dados para o golpista", "Ela ignora o problema"),
                0,
                "Perfeito! A tecnologia também funciona como um escudo, protegendo você de pessoas mal-intencionadas.",
                "Ela te protege! A mesma inteligência que ajuda na receita também vigia mensagens falsas."),
            new Questao(
                "Qual a 'regra de ouro' sobre senhas na internet?",
                Arrays.asList("Passar para qualquer pessoa que pedir educadamente",
                    "Nunca compartilhar senhas por telefone ou mensagem", "Anotar a senha na capa do celular"),
                1,
                "Exato! Suas senhas são suas chaves pessoais e não devem ser entregues a ninguém.",
                "Segurança em primeiro lugar! Guarde suas senhas com você, não importa quem peça por mensagem."),
            new Questao(
                "Se receber um áudio que parece ser do seu neto pedindo dinheiro, o que fazer?",
                Arrays.asList("Mandar o dinheiro na hora",
                    "Desconfiar e confirmar de outra forma, pois pode ser um golpe",
                    "Ignorar e nunca mais falar com o neto"),
                1,
                "Muito bem! Sempre confirme por uma ligação ou pessoalmente antes de agir, para sua proteção.",
                "Atenção redobrada! Mesmo que a foto ou voz pareçam reais, sempre verifique antes de passar valores."),
            new Questao(
                "A internet pode ser um lugar seguro para descobertas?",
                Arrays.asList("Não, é sempre um lugar perigoso",
                    "Sim, desde que você cuide bem das suas 'chaves' e senhas", "Apenas se você não clicar em nada"),
                1,
                "Isso mesmo! Com os cuidados certos, o mundo digital é incrível e cheio de coisas boas para aprender.",
                "O ambiente online é maravilhoso para quem está bem informado e protege seus dados pessoais."),
            new Questao(
                "O que fazer se você tiver um 'friozinho na barriga' ao usar a internet?",
                Arrays.asList("Desistir de usar para sempre", "Aprender a se proteger e usar a tecnologia como escudo",
                    "Entregar o celular para uma criança usar por você"),
                1,
                "Perfeito! O conhecimento é o seu melhor escudo para navegar com tranquilidade e sem medo.",
                "Sentir receio é normal no começo, mas aprender as dicas de segurança faz esse medo sumir rapidinho.")));

      default: // AVALIAÇÃO GERAL: ESTRELA DO TOPO
        return new ArrayList<>(Arrays.asList(
            new Questao(
                "Qual a melhor definição para a Inteligência Artificial apresentada na aula?",
                Arrays.asList("Um robô que quer substituir os humanos",
                    "Um secretário digital que organiza a vida e cuida da saúde",
                    "Um programa de computador muito difícil de entender", "Um brinquedo moderno para crianças"),
                1,
                "Excelente! Você compreendeu que a IA é um apoio prático e protetor para o seu dia a dia.",
                "Lembre-se da nossa conversa: ela é como um secretário prestativo que está sempre à disposição."),
            new Questao(
                "Sobre a facilidade de uso, o que é verdade?",
                Arrays.asList("É preciso ser gênio da computação",
                    "É tão simples quanto bater um papo na sala de estar", "Leva anos para aprender o básico",
                    "Apenas jovens conseguem usar sem ajuda"),
                1,
                "Isso! A tecnologia moderna se adapta a você, permitindo o uso através da fala e de comandos simples.",
                "A ideia é a simplicidade! Se você sabe conversar, você já sabe o básico para usar essa tecnologia."),
            new Questao(
                "Quais as três principais funções de saúde dos relógios inteligentes citadas?",
                Arrays.asList("Jogar videogame, tirar fotos e ver mapas",
                    "Lembrete de remédios, monitoramento cardíaco e sensor de queda",
                    "Medir a altura, pesar a comida e contar o tempo de sono",
                    "Trocar mensagens, ouvir rádio e ver notícias"),
                1,
                "Parabéns! Essas três funções são essenciais para garantir um envelhecimento seguro e tranquilo.",
                "Pense no cuidado: os relógios avisam do remédio, cuidam do coração e pedem ajuda se você cair."),
            new Questao(
                "Por que o comando de voz é considerado 'libertador'?",
                Arrays.asList("Because the cellular is louder",
                    "Porque dispensa o uso de teclados pequenos e esforço visual",
                    "Porque permite falar com pessoas de outros países", "Porque economiza a bateria do aparelho"),
                1,
                "Exato! Ele liberta você da dificuldade de enxergar letrinhas ou de apertar botões pequenos.",
                "A liberdade vem da fala! Usar a voz torna tudo mais acessível para quem tem qualquer dificuldade física."),
            new Questao(
                "Como a IA ajuda na cozinha e no lazer?",
                Arrays.asList("Fazendo as compras sozinha sem avisar",
                    "Sugerir receitas com o que tem em casa e recomendar filmes do seu gosto",
                    "Cozinhando a comida para você", "Desligando a TV na hora da novela"),
                1,
                "Isso mesmo! Ela é uma companheira que traz ideias para o seu almoço e diversão para o seu descanso.",
                "Ela atua nas pequenas coisas: dando dicas de culinária e achando aquele filme que é a sua cara."),
            new Questao(
                "Qual atitude garante sua segurança financeira no mundo digital?",
                Arrays.asList("Contar suas senhas para vizinhos",
                    "Nunca passar senhas ou dados bancários por mensagens ou telefone",
                    "Deixar as senhas salvas em um papel na geladeira",
                    "Sempre acreditar em pedidos de dinheiro por WhatsApp"),
                1,
                "Perfeito! Guardar suas senhas a sete chaves é o segredo para uma vida digital sem sustos.",
                "Proteja-se! Suas senhas são privadas e nunca devem ser compartilhadas, não importa quem peça."),
            new Questao(
                "O que o GPS faz além de mostrar o caminho?",
                Arrays.asList("Ele decide onde você vai morar", "Ele aprende suas rotas preferidas e evita o trânsito",
                    "Ele avisa quando o combustível acaba", "Ele escolhe quem você deve visitar"),
                1,
                "Isso! O GPS se torna inteligente com o tempo, conhecendo os caminhos que você mais gosta de fazer.",
                "Ele é um assistente de viagens: decora seus caminhos para a casa dos filhos e evita caminhos chatos."),
            new Questao(
                "A IA é comparada a qual profissional para explicar sua utilidade?",
                Arrays.asList("Um guarda de trânsito", "Um secretário particular 24 horas",
                    "Um professor de matemática", "Um médico cirurgião"),
                1,
                "Correto! Ela está sempre de prontidão para organizar seus lembretes e responder suas dúvidas.",
                "Imagine que você tem um secretário digital: ele nunca tira férias e está sempre pronto para te ajudar."),
            new Questao(
                "Qual o papel do sensor de queda para a autonomia do idoso?",
                Arrays.asList("Permite que ele corra mais rápido",
                    "Dá confiança para morar e caminhar sozinho, sabendo que o socorro é automático",
                    "Impede que a pessoa caia", "Avisa quando é hora de sentar"),
                1,
                "Exatamente! Saber que o socorro será chamado sozinho traz uma liberdade e paz de espírito enormes.",
                "O sensor traz coragem! Com ele, você se sente mais seguro para realizar suas atividades do dia a dia."),
            new Questao(
                "O que significa dizer que a IA 'reconhece padrões'?",
                Arrays.asList("Que ela sabe costurar roupas",
                    "Que ela aprende seus gostos e hábitos para facilitar sua vida",
                    "Que ela desenha figuras geométricas", "Que ela só entende coisas repetidas"),
                1,
                "Muito bem! Ela percebe o que você gosta e o que você precisa, tornando-se cada vez mais útil para você.",
                "Aprender com você é o segredo! Ela entende seu jeito e seus horários para te ajudar cada vez melhor.")));
    }
  }

  private static List<Questao> obterQuestoesAtencao(int idAtividade) {
    return new ArrayList<>(Arrays.asList(
        new Questao(
            "Questão de Atenção (Modelo)?",
            Arrays.asList("Alternativa 1", "Alternativa 2 (Correta)"),
            1,
            "Acertou a questão de Atenção!",
            "Ops, tente ler com mais atenção!")));
  }

  private static List<Questao> obterQuestoesLogica(int idAtividade) {
    return new ArrayList<>(Arrays.asList(
        new Questao(
            "Questão de Lógica (Modelo)?",
            Arrays.asList("Resposta Correta", "Resposta Incorreta"),
            0,
            "Ótimo raciocínio lógico!",
            "Raciocínio incorreto, vamos praticar mais!")));
  }

  private static List<Questao> obterQuestoesLinguagem(int idAtividade) {
    return new ArrayList<>(Arrays.asList(
        new Questao(
            "Questão de Linguagem (Modelo)?",
            Arrays.asList("Correto", "Incorreto"),
            0,
            "Muito bem!",
            "Tente novamente!")));
  }
}
