# SeeMath

SeeMath is a companion application to [Hypatia's](https://hypatiasys.com/) math editor software for mathematical
visualizations.

As a team, we believe that the visualization of mathematical problems gives students different perspectives to help them
solve these problems. With this app, we aim to improve the learning process of younger grade students that are getting
introduced to the basic mathematical concepts like addition, multiplication, division as well as function graphing.

SeeMath allows anyone using Hypatia's Math Editor to easily visualize certain mathematical expressions. In addition,
whenever a student makes a mathematical mistake in Hypatia's editor, our visualizations are automatically displayed as
a hint directly to Hypatia's editor, helping them understand and visualize their mistake.

## Getting Started

To run SeeMath on your local machine clone this GitHub repository and make sure you have the following prerequisites
installed:

### Prerequisites

 * [Hypatia's Desktop Math Editor](https://hypatiasys.com/downloads/latest)
 * [Maven](https://maven.apache.org/)

### Installing

Once you have all the prerequisites installed, open the terminal and run the following command from inside SeeMath's
repository to obtain all the necessary dependencies:

```
mvn clean install
```

Now you have everything you need to run SeeMath.

### Running SeeMath

Since our project is a companion app, you must first open your Hypatia Desktop. This is how you will primarily interact
with SeeMath.

From inside SeeMath's repository, run the following command:

```
mvn clean javafx:run
```

This should pop up SeeMath's landing page and our app is ready to go.

## Usage

From this point on, SeeMath will automatically generate visualizations to the equations you type in Hypatia. As an
example, say we want to visualize why
<img src="https://render.githubusercontent.com/render/math?math=\frac{2}{5}"> + <img src="https://render.githubusercontent.com/render/math?math=\frac{1}{2} = \frac{9}{10}">.

Watch this [video](https://youtu.be/mEn8E5QmVtA?t=155) (4 min) for a demo walkthrough of the project.

To do so, we just type that equation in Hypatia and wait until Hypatia's CheckMath validates the expression:

(Hypatia Editor with CheckMath Circled)

Once that is done, you can go over to the SeeMath window and contemplate the visualization.

(Fraction Visualization Picture)

Notice you can also visualize other types of expressions:

(Mixed Expressions Picture)

And even some functions:

(Graph Picture)

You may also save and access any visualization you like by go under File -> Export:

(Export Visualization Picture)

## Built With

* [JavaFX](https://openjfx.io/) - Client application platform
* [Maven](https://maven.apache.org/) - Dependency Management
* [SocketIO](https://socket.io/) - Library for realtime web application

## Authors

* **Tales Scopinho** - [talicopanda](https://github.com/talicopanda)
* **Henning Lindig** - [Hennmeister](https://github.com/Hennmeister)
* **Jacob Sahlmueller** - [jacob-sahl](https://github.com/jacob-sahl)
* **Affanullah Siddiqui** - [affansidd](https://github.com/affansidd)
* **Piyush Sharma** - [piyush-sharma](https://github.com/piyush-sharma)


## License

This project is licensed under the MIT License

## Acknowledgments

This project was developed as part of the
[Technology Leadership Initiative](https://www.technologyleadershipinitiative.com/) partnered with
[Hypatia](https://hypatiasys.com/).

For that, we are extremely thankful for the mentorship and support from:

* Michael McCarthy
* Karen Reid
* Jonathan Galperin
* Avery Laird
* Tony Nguyen
* William Ganyo


