public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Expected input filename");
            return;
        }

        Solver solver = new Solver(args[0]);
        solver.solve();
    }
}