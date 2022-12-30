
# Titro - Android App

![Titro Icon](https://i.postimg.cc/wT9M3R2n/splash-icon-1.png "Titro Icon")

## Sobre

***PT-BR***

Titro é um aplicativo móvel de anotações para dispositivos Android.

Titro foi desenvolvido em Kotlin junto ao moderno conjunto de ferramentas para UI, Jetpack Compose.
O aplicativo possui múltiplas telas, porém somente uma única activity e zero fragmentos,
isso graças ao novo método de construir interfaces de forma declarativa utilizando o Compose.

Titro conta com a biblioteca ROOM para ler e salvar todos os dados localmente,
um ViewModel que contém toda a lógica necessária para o funcionamento do aplicativo,
junto a biblioteca Dagger-Hilt para Injeção de Dependência,
Preference DataStore que persiste um par de chave-valor simples,
Compose Navigation para a navegação entre as telas Composable,
há também uma tela Splash animada e transições animadas entre a navegação de telas assim como em alguns outros componentes que dão um toque atraente ao app.

Os diversos componentes e camadas do projeto foram estruturados utilizando os padrões de arquitetura MVVM e Clean Architecture.
____

***EN***

Titro is a note-taking mobile application for Android devices.

Titro was developed using a modern declarative UI Toolkit called Jetpack Compose and Kotlin language.
The application has multiple screens but a single activity only and zero fragments,
thanks to the new method of building interfaces using Jetpack Compose.

Titro relies on ROOM library to read and locally save all data,
a ViewModel which contains the logic needed to make the app work,
along with that there's the Dagger-Hilt library for Dependency Injection,
Preference DataStore to persist a simple key-value pair,
Compose Navigation to navigate between screen Composables,
there's also an animated Splash screen and animated transitions between screens navigation as in some other components that gives the app a nice touch.

All different components and layers in the project were structured using MVVM and Clean Architecture as architectural patterns.

## Características

***PT-BR***

- Adicionar, salvar, editar e deletar anotações
- Procurar anotações pelo título
- Deletar anotações ao arrastá-las para esquerda
- Definir e listar anotações por baixa, média ou alta prioridade
- Temas Escuro e Claro disponíveis
____

***EN***

- Add, save, edit and delete notes
- Search notes by their title
- Delete notes by swiping them to the left
- Set and sort notes by low, medium or high priority
- Dark and Light themes available


## Screenshots

[![Exibição de lista de anotações Modo claro](https://i.postimg.cc/gc33n4cR/notes-light-demo.png)](https://postimg.cc/V5LJhqCL)

[![Exibição de lista de anotações Modo escuro](https://i.postimg.cc/BbfDmPpX/notes-demo.png)](https://postimg.cc/8Jwj55qS)

[![Busca de anotações na base de dados](https://i.postimg.cc/6qVZg6bz/search-demo.png)](https://postimg.cc/SYsjz4hz)

[![Adicionar uma nova anotação](https://i.postimg.cc/pdyDrHyX/add-note-demo.png)](https://postimg.cc/7C8Ctjgp)

[![Deletar uma anotação](https://i.postimg.cc/yN50SbSh/delete-confrmation.png)](https://postimg.cc/kVQ20wsD)

[![Demostração de base de dados vazia](https://i.postimg.cc/mDHC3yQv/no-data.png)](https://postimg.cc/47XYst2P)

## Testando o aplicativo

Baixe o APK para testar o app em seu smartphone ou emulador

```bash
  https://drive.google.com/uc?id=1yYRcxaNQAfGGZZ3irG9MGttEtMrkAX_h&export=download
```