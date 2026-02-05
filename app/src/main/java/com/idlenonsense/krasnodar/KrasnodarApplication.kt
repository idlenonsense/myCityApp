package com.idlenonsense.krasnodar

import com.idlenonsense.krasnodar.data.PlaceRepository

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.idlenonsense.krasnodar.data.Category
import com.idlenonsense.krasnodar.data.Place
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class KrasnodarApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    val database: KrasnodarDatabase by lazy {
        Room.databaseBuilder(
            this,
            KrasnodarDatabase::class.java,
            "krasnodar_database"
        )
            .addCallback(PlaceDatabaseCallback(applicationScope)) // Подключаем наш Callback
            .build()
    }

    val repository: PlaceRepository by lazy {
        PlaceRepository(database.placeDao())
    }

    private class PlaceDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            instance?.let { database ->
                scope.launch {
                    val placeDao = database.placeDao()
                    val initialPlaces = listOf(
                        Place(name = "Уни-пицца", desc = "Ул. Московская, 1" +
                                "\n" +
                                "Средний чек: 200 рублей" +
                                "\n" +
                                "Бoльшoй выбoр пицц, рaзличныe виды суши и рoллoв, изыскaнныe дeсeрты и нaпитки, сaлaты и гoрячиe блюдa.\n", imageUrl = "https://avatars.mds.yandex.net/get-altay/992583/2a000001629aafaa01d1f0e0dfe30d4ea5e0/XXL_height", category = Category.FOOD),
                        Place(name = "Краснодарский парень", desc = "Ул. Северная, 358" +
                                "\n" +
                                "Средний чек: 500 рублей" +
                                "\n" +
                                "«Краснодарский парень» — это кафе с широким выбором бургеров, крафтовым пивом и недорогими комплексными обедами.\n" +
                                "Гости отмечают, что бургеры здесь очень вкусные, сочные и большие, а картофель фри — хрустящий и подается с различными соусами.\n", imageUrl =  "https://avatars.mds.yandex.net/get-altay/9686455/2a00000189d6a06037fc857ce2927ce272cf/XXL_height", category = Category.FOOD),
                        Place(name = "Тори Рамен", desc = "Ул. Красная, 174/3" +
                                "\n" +
                                "Средний чек: 450 рублей" +
                                "\n" +
                                "Tori Ramen — это ресторан быстрого питания, специализирующийся на азиатской кухне.\n" +
                                "Здесь можно попробовать разнообразные блюда, такие как тайский суп, лапша, роллы, курица по-пекински и многое другое.\n", imageUrl =  "https://avatars.mds.yandex.net/get-altay/18184740/2a0000019aacbdf35c1c299fe45b853fcbe5/XXL_height", category = Category.FOOD),
                        Place(name = "Мост поцелуев", desc = "Молодёжная площадь" +
                                "\n" +
                                "«Мост поцелуев» — это достопримечательность в Краснодаре, которая соединяет Кубанскую набережную с Затоном.\n" +
                                "На мосту можно увидеть красивые виды на реку Кубань и город Краснодар.\n", imageUrl =  "https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/%22%D0%9F%D0%BE%D1%86%D0%B5%D0%BB%D1%83%D0%B5%D0%B2_%D0%BC%D0%BE%D1%81%D1%82%22_-_panoramio.jpg/960px-%22%D0%9F%D0%BE%D1%86%D0%B5%D0%BB%D1%83%D0%B5%D0%B2_%D0%BC%D0%BE%D1%81%D1%82%22_-_panoramio.jpg", category = Category.VISIT),
                        Place(name = "Парк Краснодар", desc = "Городской сад" +
                                "\n" +
                                "Парк «Краснодар» знаменит своей архитектурой и ландшафтным дизайном по всей России и за ее пределами.\n" +
                                "Успешные проекты в сфере паблик-арта здесь столь же удивительны и разнообразны, как и сам парк.\n", imageUrl =  "https://avatars.mds.yandex.net/get-vertis-journal/4471904/3.jpg_1743843118895/orig", category = Category.VISIT),
                        Place(name = "Немецкая деревня", desc = "Немецкая деревня - одно из потрясающих мест, где можно провести день ни о чём не думая.\\n\n" +
                                "Это настоящая копия популярного городка Баден-Баден в Краснодаре, которую полюбили все, кто здесь когда-либо был.\n", imageUrl =  "https://i.ytimg.com/vi/2e73_MRMoeM/maxresdefault.jpg?sqp=-oaymwEmCIAKENAF8quKqQMa8AEB-AH-CYAC0AWKAgwIABABGB8gQih_MA8=&rs=AOn4CLBMBw3ap82RPuQojMrgjlPuHWDx8g", category = Category.VISIT),
                        Place(name = "Кинотеатр Каро-8", desc = "Ул. Володи Головатого, 313" +
                                "\n" +
                                "Кинотеатр «КАРО 8» — это место, где вы можете насладиться просмотром фильмов в удобных креслах, с выдвижными подставками для ног, а также зарядить свой телефон с помощью беспроводной зарядки.\n", imageUrl =  "https://avatars.mds.yandex.net/get-altay/15401795/2a000001962ee91d060d7b67be13c96255ce/XXL_height", category = Category.FUN),
                        Place(name = "The Rock Bar", desc = "Ул. Горького, 104" +
                                "\n" +
                                "The Rock Bar — это рок-бар, где звучит живая музыка, выступают кавер-группы и приглашенные рок-музыканты.\n" +
                                "Здесь можно послушать выступления российских рок-кавер-групп и отлично провести время.\n", imageUrl =  "https://i.timeout.ru/pix/479736.jpeg", category = Category.FUN),
                        Place(name = "Хмельная Марта", desc = "Ул. имени Дзержинского, 35" +
                                "\n" +
                                "В баре «Хмельная Марта» вы можете насладиться свежим пивом, сваренным в собственной пивоварне, а также различными сортами пива, включая лагеры и эли. Гости отмечают, что пиво имеет насыщенный и плотный вкус, который им нравится.\n", imageUrl =  "https://irecommend.ru/sites/default/files/imagecache/copyright1/user-images/2895874/aJaYRu1nKAB4df8mpFWwxA.jpg", category = Category.FUN),
                        Place(name = "Crowne Plaza", desc = "Ул. Красная, 109" +
                                "\n" +
                                "Гостиница Crowne Plaza Krasnodar Centre предлагает своим гостям комфортное проживание в просторных номерах с удобными кроватями и ортопедическими матрасами.\n" +
                                "В каждом номере есть набор чая, кофе и растворимого шоколада, а также печенье.\n", imageUrl =  "https://images.putevka.com/4553_2506191634033.jpg", category = Category.HOTELS),
                        Place(name = "Art Hotel", desc = "Ул. Леваневского, 156/1" +
                                "\n" +
                                "Art Hotel расположен в центре города Краснодара. В шаговой доступности все достопримечательности города.\n" +
                                "Это замечательное место для семейного проживания, отдыха для деловых людей и туристов.\n", imageUrl =  "https://q-xx.bstatic.com/xdata/images/hotel/max1024x768/198076624.jpg?k=b5f98f0937073f5c95e30d3d35490064d1dba853aec10cc09994a2b8e2244d22&o=", category = Category.HOTELS),
                        Place(name = "Hollywood Deluxe", desc = "Ул. Северная, 219" +
                                "\n" +
                                "Современный отель категории в центре города со своей тематикой, где вы сможете почувствовать себя голливудской звездой, пройтись по алее славы, познакомиться с фотогаллереей звезд, таких как Аль Пачино, Дженнифер Лопес, Антонио Бандерас и многими другими.\n", imageUrl =  "https://q-xx.bstatic.com/xdata/images/hotel/max1024x768/142542132.jpg?k=3a2463d6edec04b68f271bdac1fccbcd63162dc1141a21054cb2be549f9bbab1&o=", category = Category.HOTELS)
                    )

                    placeDao.insertPlaces(initialPlaces)
                }
            }
        }
    }

    companion object {
        private var instance: KrasnodarDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = database
    }
}