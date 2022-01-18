public class Reduction {

	public static void main(String[] args) {
		int reputation;

		reputation = 1_000;
		System.out.println("Start: " + reputation);
		for(int i = 0; i < 100; i++) {
			reputation = diminish(reputation);
		}
		System.out.println("End: " + reputation);

		reputation = 5_000;
		System.out.println("Start: " + reputation);
		for(int i = 0; i < 100; i++) {
			reputation = diminish(reputation);
		}
		System.out.println("End: " + reputation);

		reputation = 15_000;
		System.out.println("Start: " + reputation);
		for(int i = 0; i < 100; i++) {
			reputation = diminish(reputation);
		}
		System.out.println("End: " + reputation);

		reputation = 50_000;
		System.out.println("Start: " + reputation);
		for(int i = 0; i < 100; i++) {
			reputation = diminish(reputation);
		}
		System.out.println("End: " + reputation);
	}

	public static int diminish(int reputation) {
		double reduction = reputation;
		reduction = Math.pow(reduction, 1.4);
		reduction *= 0.000_05;

		reputation = (int) Math.max(reputation - reduction, 0);
		return reputation;
	}
}
