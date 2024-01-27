package com.czech.swapi

//{
//    "imie": "Anna",
//    "wiek": 25,
//    "miasto": "Kraków"
//}

data class Person1(
    val imie: String,
    val wiek: Int,
    val miasto: String
)

//{
//    "nazwa": "Samsung S23",
//    "cena": 4299.99,
//    "specyfikacja":
//    {
//        "procesor": "Snapdragon",
//        "ram": "8 GB",
//        "bateria": "5000 mAh"
//    }
//}
data class Product(
    val nazwa: String,
    val cena: Double,
    val specyfikacja: Specyfikacja
)

data class Specyfikacja(
    val procesor: String,
    val ram: String,
    val bateria: String
)

//{
//    "owoce": [
//    { "nazwa": "Jabłko", "kolor": "czerwony" },
//    { "nazwa": "Banan", "kolor": "żółty" },
//    { "nazwa": "Pomarańcza", "kolor": "pomarańczowy" }
//    ]
//}

data class Fruits(
    val owoce: List<Fruit>
)

data class Fruit(
    val nazwa: String,
    val kolor: String
)

//{
//    "drzewa": [
//    {
//        "nazwa": "Dąb",
//        "wysokość": 20,
//        "informacje": {
//        "kraj": "Polska",
//        "powierzchnia": 312696
//    }
//    },
//    {
//        "nazwa": "Sosna",
//        "wysokość": 15,
//        "informacje": {
//        "kraj": "Szwecja",
//        "powierzchnia": 450295
//    }
//    },
//    {
//        "nazwa": "Jesion",
//        "wysokość": 18,
//        "informacje": {
//        "kraj": "Francja",
//        "powierzchnia": 551695
//    }
//    }
//    ]
//}

data class Forest(
    val drzewa: List<Tree>
)

data class Tree(
    val nazwa: String,
    val wysokość: Int,
    val informacje: Informacje
)

data class Informacje(
    val kraj: String,
    val powierzchnia: Int
)
//{
//    "samochody": [
//    {
//        "marka": "Toyota",
//        "model": "Corolla",
//        "rok": 2022,
//        "informacje": {
//        "silnik": {
//        "typ": "Benzynowy",
//        "pojemnosc": "1.8L",
//        "moc": "140 KM"
//    },
//        "nadwozie": {
//        "typ": "Sedan",
//        "kolor": "Czarny"
//    }
//    },
//        "wyposazenie": ["klimatyzacja", "nawigacja", "tempomat"]
//    },
//    {
//        "marka": "Honda",
//        "model": "Civic",
//        "rok": 2022,
//        "informacje": {
//        "silnik": {
//        "typ": "Hybryda",
//        "pojemnosc": "1.5L",
//        "moc": "120 KM"
//    },
//        "nadwozie": {
//        "typ": "Hatchback",
//        "kolor": "Czerwony"
//    }
//    },
//        "wyposazenie": ["klimatyzacja", "kamera cofania", "system bezpieczeństwa"]
//    }
//    ]
//}

data class Cars(
    val samochody: List<Samochod>
)

data class Samochod(
    val marka: String,
    val model: String,
    val rok: Int,
    val informacje: AutoInformacje,
    val wyposazenie: List<String>
)

data class AutoInformacje(
    val silnik: Silnik,
    val nadwozie: Nadwozie
)

data class Silnik(
    val typ: String,
    val pojemnosc: String,
    val moc: String
)

data class Nadwozie(
    val typ: String,
    val kolor: String
)
