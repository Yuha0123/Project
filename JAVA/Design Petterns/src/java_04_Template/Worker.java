package java_04_Template;

public abstract class Worker {
	
	//�߻� �޼ҵ�//
	//������ ������ �ۼ��ϴ� �޼ҵ�///
	protected abstract void doit();
	
	//final ������ �ڽ�Ŭ�������� ������ ��������//
	public final void work(){
		System.out.println("���");
		doit();
		System.out.println("���");
	}
}
