package java_03_Factory;

public class AnimalFactory {
	
	//Factory Method//
	public static Animal create(String animalName){
		if(animalName == null){
			throw new IllegalArgumentException("NULL");
		}
		
		if(animalName.equals("��")){
			return new Cow();
		} else if(animalName.equals("�����")){
			return new Cat();
		} else if(animalName.equals("������")){
			return new Dog();
		} else {
			return null;
		}
	}
}
