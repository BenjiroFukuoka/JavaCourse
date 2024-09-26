package release.details;

/* Абстрактный класс деталей*/
public abstract class DetailTemplate {
    /* Каждая деталь имеет свой айди */
    private int id;

    /* Получаем айди при создании объекта по классу */
    DetailTemplate(int id) {
        this.id = id;
    }

    /* Узнать айди объекта */
    public int getId() {
        return id;
    }
}