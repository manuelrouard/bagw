package be.mrouard;

import be.mrouard.model.entity.Bag;
import be.mrouard.model.entity.Trans;
import be.mrouard.model.service.BagService;

public class TestXStream {

	public static void main(String[] args) {
		BagService bagService=new BagService();
		
		try {
			Bag bag=bagService.openFile();
			Trans t=new Trans();
			t.setId(1);
			t.setType("D");
			t.setAmount(10);
			
			bag.addTrans(t);
			bagService.saveFile(bag);
			System.out.println("Test completed");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
