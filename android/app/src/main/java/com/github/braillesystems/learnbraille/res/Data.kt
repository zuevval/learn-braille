package com.github.braillesystems.learnbraille.res

import com.github.braillesystems.learnbraille.data.dsl.annotate
import com.github.braillesystems.learnbraille.data.dsl.data
import com.github.braillesystems.learnbraille.data.dsl.lessons
import com.github.braillesystems.learnbraille.data.entities.*
import com.github.braillesystems.learnbraille.data.entities.BrailleDot.F


object SymbolType {
    const val ru = "ru"
}

object StepAnnotation {
    const val someBookRequired = "some_book_required"
}


val prepopulationData by data(
    materials = content
) {

    users {
        user(
            login = "default",
            name = "John Smith"
        )
    }

    courses {
        course(
            name = "По Голубиной, но не совсем",
            description = "Совсем не совсем"
        ) {
            +golubinaLessons
            +someMoreGolubinaLessons
        }

        annotations {
            +StepAnnotation.someBookRequired
        }
    }

    decks {
        deck("Русские буквы") { data ->
            data is Symbol && data.type == SymbolType.ru
        }
    }
}

private val golubinaLessons by lessons {

    val name1 = "Знакомство с шеститочием"
    lesson(
        name = name1
    ) {
        +FirstInfo(
            """Перед Вами пошаговый курс для обучения с нуля системе Луи Брайля.
            |Курс построен на основе книги В. В. Голубиной "Пособие по изучению системы Л. Брайля".
            |В течение курса Вы ознакомитесь с обозначениями букв, цифр и специальных символов.
            |В некоторых шагах предлагается выполнять задания, используя книгу Голубиной.
            |Если Вам будет непонятно, как пользоваться курсом, Вы в любой момент можете вызвать справку
            |по разделу, нажав кнопку "справка" в правом верхнем углу экрана.
            """
        )
        +Info(name1)
        +Info(
            """В рельефной азбуке Брайля любой символ - это шеститочие. 
                |Каждая точка из шести может быть выдавлена или пропущена. 
                |В следующем шаге все 6 точек выведены на экран."""
        )
        +ShowDots(
            text = "Перед Вами полное шеститочие",
            dots = BrailleDots(F, F, F, F, F, F)
        )
        +InputDots(
            text = "Введите все шесть точек",
            dots = BrailleDots(F, F, F, F, F, F)
        )
        +Info(
            """Откройте букварь на странице 12. 
                |В верхней строке 14 раз повторён символ полного шеститочия."""
        )
        +Info(
            """Точки расположены в два столбца по три. 
                |Точки в первом столбце имеют номера 1, 2, 3 сверху вниз. 
                |Точки во втором столбце - 4, 5, 6 сверху вниз. 
                |Важно выучить, где какая точка."""
        )
    }

    lesson(
        name = "Русские буквы А, Б, Ц"
    ) {
        +Info(
            """Некоторые символы Брайля обозначают как букву, так и цифру. Это буквы А, Б,
                    |Ц и так далее. С добавлением цифрового знака, который мы изучим в следующем
                    |уроке, из них получаются цифры 1, 2, 3 и так далее."""
        )
        +Info(
            """Буква А обозначается одной точкой, точкой номер один.
                    |Ознакомьтесь с ней."""
        )
        +Show(content.symbols.getValue('A'))
        +Info(
            """Откройте букварь на странице 13. Вверху слева рельефно-графическое
                    |изображение буквы А. Рядом после полного шеститочия пять раз повторена 
                    |буква А точечным шрифтом."""
        )
        +Input(content.symbols.getValue('А'))
        +Show(content.symbols.getValue('Б'))
        +Info(
            """Снова изучим страницу 13 в букваре. Под строкой с буквой А - 
                    |такая же с буквой Б."""
        ).annotate(StepAnnotation.someBookRequired)
        +Input(content.symbols.getValue('Б'))
        +Info(
            """Ознакомьтесь с буквой Ц на странице 13 букваря. 
                    |Строка с буквой Ц находится под строкой с буквой Б."""
        ).annotate(StepAnnotation.someBookRequired)
        +Show(content.symbols.getValue('Ц'))
        +Input(content.symbols.getValue('Ц'))
        +Info(
            """Поздравляем! Второй урок пройден. В следующем занятии мы узнаем, 
                    |как с помощью букв А, Б и Ц составить цифры 1, 2 и 3."""
        )
        +LastInfo(
            """Вы дошли до конца курса. Спасибо, что воспользовались нашим обучающим
            |приложением! Вы всегда можете вернутся к ранее пройденному материалу и повторить его.
            |Продолжение следует!
            """
        )
    }
}

private val someMoreGolubinaLessons by lessons {
    // ...
}
