import java.util.*; // Optional but useful



public class LibrarySimulation {
    public static void main(String[] args) {
        Library library = new Library();

        // Add sample books
        library.addBook(new Book("1984", "George Orwell"));
        library.addBook(new Book("To Kill a Mockingbird", "Harper Lee"));
        library.addBook(new Book("The Great Gatsby", "F. Scott Fitzgerald"));
        library.addBook(new Book("Brave New World", "Aldous Huxley"));

        // Create and start patron threads
        Thread[] patrons = new Thread[3];
        for (int i = 0; i < patrons.length; i++) {
            Patron patron = new Patron("Patron " + (i + 1), library);
            library.addPatron(patron); // Register patron
            patrons[i] = new Thread(patron);
            patrons[i].start();
        }

        // Wait for all threads to finish
        for (Thread patron : patrons) {
            try {
                patron.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Library simulation complete.");
    }
}
