package utility;

import exceptions.EmptyCollectionException;
import exceptions.WrongArgumentException;
import exceptions.WrongIDException;
import labwork.LabWork;
import labwork.Location;
import labwork.Person;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class CollectionManager {
    private static int idCounter;
    private HashMap<String, LabWork> labWorks = new HashMap<String, LabWork>();
    private LocalDate creationDate;
    private FileManager fileManager;

    public CollectionManager(FileManager fileManager) {
        this.fileManager = fileManager;
        creationDate = LocalDate.now();
    }

    public void loadCollection() {
        HashMap<String, LabWork> trr = fileManager.loadCollection();
        if (trr != null) {
            labWorks = trr;
        }
    }

    public String addLabWorkToCollection(String key, LabWork labWork) {
        labWorks.put(key, labWork);
        return "Элемент успешно добавлен.\n";
    }

    public String labWorkToOutput(String key, LabWork labWork) {
        StringBuilder answer = new StringBuilder();
        answer.append("key: ").append(key).append("\n");
        answer.append("id: ").append(labWork.getId()).append("\n");
        answer.append("    Название: ").append(labWork.getName()).append("\n");
        answer.append("     Координаты:" + "\n");
        answer.append("        x: ").append(labWork.getCoordinates().getX()).append("\n");
        answer.append("        y: ").append(labWork.getCoordinates().getY()).append("\n");
        answer.append("    Дата создания: ").append(labWork.getCreationDate()).append("\n");
        answer.append("    Минимальный балл: ").append(labWork.getMinimalPoint()).append("\n");
        answer.append("    Мин. балл за л.к.: ").append(labWork.getPersonalQualitiesMinimum()).append("\n");
        answer.append("    Средний балл: ").append(labWork.getAveragePoint()).append("\n");
        try {
            answer.append("    Сложность: ").append(labWork.getDifficulty().getName()).append("\n");
        } catch (Exception e) {
            answer.append("    Сложность: ").append("null").append("\n");
        }
        Person author = labWork.getAuthor();
        if (author != null) {
            Location authorLocation = author.getLocation();
            answer.append("    Автор:" + "\n");
            answer.append("        Имя: ").append(author.getName()).append("\n");
            answer.append("        Рост: ").append(author.getHeight()).append("\n");
            try {
                answer.append("        Цвет глаз: ").append(author.getEyeColor().getName()).append("\n");
            } catch (Exception e) {
                answer.append("        Цвет глаз: ").append("null").append("\n");
            }
            try {
                answer.append("        Цвет волос: ").append(author.getHairColor().getName()).append("\n");
            } catch (Exception e) {
                answer.append("        Цвет волос: ").append("null").append("\n");
            }
            try {
                answer.append("        Национальность: ").append(author.getNationality().getName()).append("\n");
            } catch (Exception e) {
                answer.append("        Национальность: ").append(author.getNationality().getName()).append("\n");
            }
            answer.append("        Местоположение: " + "\n");
            answer.append("            Локация: ").append(authorLocation.getName()).append("\n");
            answer.append("                x: ").append(authorLocation.getX()).append("\n");
            answer.append("                y: ").append(authorLocation.getY()).append("\n");
            answer.append("                z: ").append(authorLocation.getZ()).append("\n");
        } else
            answer.append("    Автор: null" + "\n" + "\n");
        return answer.toString();
    }

    public String showCollection() {
        String answer;
        try {
            if (!labWorks.isEmpty()) {
                Set<Map.Entry<String, LabWork>> labsForOutput = labWorks.entrySet();
                StringBuilder answerBuilder = new StringBuilder();
                for (Map.Entry<String, LabWork> i : labsForOutput) {
                    answerBuilder.append(labWorkToOutput(i.getKey(), i.getValue()));
                }
                answer = answerBuilder.toString();
            } else throw new EmptyCollectionException();

        } catch (EmptyCollectionException emptyCollectionException) {
            answer = "В коллекции нет элементов\n";
        }
        return answer;
    }

    public String showInfo() {
        String ans = "";
        ans +=
                "Информация о коллекции:"
                        + "  Тип: Hashmap <String, LabWork>\n"
                        + "  Дата создания:" + creationDate + " \n"
                        + "  Количество элементов:" + labWorks.size() + " \n";
        return ans;
    }

    public String clearCollection() {
        labWorks.clear();
        return "Коллекция очищена \n";
    }

    public Map.Entry<String, LabWork> findByID(int ID) throws WrongIDException, EmptyCollectionException {
        if (labWorks.isEmpty())
            throw new EmptyCollectionException();
        Set<Map.Entry<String, LabWork>> labs = labWorks.entrySet();
        for (Map.Entry<String, LabWork> i : labs) {
            if (i.getValue().getId() == ID) {
                System.out.println("Элемент с ID " + ID + " найден");
                return i;
            } else {
                throw new WrongIDException();
            }
        }
        return null;
    }

    public String removeKey(String key) {
        try {
            labWorks.remove(key);
            return "Ключ успешно удалён\n";
        } catch (NullPointerException e) {
            return "Ключ " + key + " не обнаружен\n";
        } catch (Exception e) {
            return "Ошибка. Невозможно удалить элемент по ключу " + key + "\n";
        }
    }

    public LabWork getByKey(String key) throws WrongArgumentException {
        LabWork labWork;
        if (labWorks.containsKey(key))
            labWork = labWorks.get(key);
        else throw new WrongArgumentException();

        return labWork;
    }

    public String removeGreaterKey(String key) {
        try {
            if (key == null) throw new WrongArgumentException();
            if (!labWorks.isEmpty()) {
                labWorks.entrySet().removeIf(stringLabWorkEntry -> key.compareTo(stringLabWorkEntry.getKey()) < 0);
                return "Команда выполнена\n";
            } else throw new EmptyCollectionException();
        } catch (EmptyCollectionException emptyCollectionException) {
            return "В коллекции нет элементов\n";
        } catch (WrongArgumentException WrongArgumentException) {
            return "Аргумент не может быть пустой строкой\n";
        } catch (Exception e) {
            e.printStackTrace();
            return "Непредвиденная ошибка\n";
        }
    }

    public String groupCountingByCrDate() {
        try {
            if (!labWorks.isEmpty()) {
                HashMap<LocalDate, Integer> labsHashMap = new HashMap<LocalDate, Integer>();
                Set<Map.Entry<String, LabWork>> labsSet = labWorks.entrySet();
                for (Map.Entry<String, LabWork> i : labsSet) {
                    String key = i.getKey();
                    LabWork labWork = i.getValue();
                    if (!labsHashMap.containsKey(labWork.getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
                        labsHashMap.put(labWork.getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), 1);
                    } else {
                        int sum = labsHashMap.get(labWork.getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        sum++;
                        labsHashMap.remove(labWork.getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        labsHashMap.put(labWork.getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), sum);
                    }
                }
                StringBuilder s = new StringBuilder();
                for (Map.Entry<LocalDate, Integer> i : labsHashMap.entrySet()) {
                    s.append(i.getKey()).append(" - ").append(i.getValue()).append("\n");
                }
                return s.toString();
            } else return "В коллекции нет ни одного элемента.\n";
        } catch (Exception e) {
            return "Ошибка\n";
        }
    }

    public String filterGreaterThanAveragePoint(float averagePoint) {
        try {
            StringBuilder s = new StringBuilder();
            if (!labWorks.isEmpty()) {
                boolean trigger = false;
                Set<Map.Entry<String, LabWork>> labsForOutput = labWorks.entrySet();
                for (Map.Entry<String, LabWork> i : labsForOutput) {
                    if (i.getValue().getAveragePoint() > averagePoint) {
                        s.append(labWorkToOutput(i.getKey(), i.getValue())).append("\n");
                        trigger = true;
                    }
                }
                if (!trigger) {
                    return "Элементов с average_point большим " + averagePoint + " не обнаружено \n";
                }
                return s.toString();
            } else throw new EmptyCollectionException();

        } catch (EmptyCollectionException emptyCollectionException) {
            return "В коллекции нет элементов";
        }
    }

    public String printDescending() {
        Vector<Map.Entry<String, LabWork>> labWorks1 = new Vector<Map.Entry<String, LabWork>>();
        try {
            if (!labWorks.isEmpty()) {
                Set<Map.Entry<String, LabWork>> labs = labWorks.entrySet();
                labWorks1.addAll(labs);
                labWorks1.sort((o1, o2) -> -o1.getValue().getName().compareTo(o2.getValue().getName()));
                StringBuilder s = new StringBuilder(";");
                for (Map.Entry<String, LabWork> i : labWorks1) {
                    s.append(labWorkToOutput(i.getKey(), i.getValue()));
                }
                return s.toString();
            } else throw new EmptyCollectionException();

        } catch (EmptyCollectionException emptyCollectionException) {
            return "В коллекции нет элементов\n";
        }
    }

    public void saveCollection() {
        fileManager.saveCollection(labWorks);
    }
}
