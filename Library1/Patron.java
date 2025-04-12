import java.util.List;
import java.util.Random;

public class Patron implements Runnable {
    private String name;
    private Library library;
    private Random random = new Random();

    public Patron(String name, Library library) {
        this.name = name;
        this.library = library;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        List<Book> availableBooks = library.listAvailableBooks();
        for (int i = 0; i < 3; i++) { // Try to borrow and return 3 books
            if (!availableBooks.isEmpty()) {
                Book book = availableBooks.get(random.nextInt(availableBooks.size()));
                
                synchronized (library) {
                    boolean success = library.checkOutBook(this, book, 3);
                    if (success) {
                        System.out.println(name + " borrowed: " + book.getTitle());
                    } else {
                        System.out.println(name + " failed to borrow: " + book.getTitle());
                    }
                }

                try {
                    Thread.sleep(random.nextInt(1000)); // Simulate usage time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                synchronized (library) {
                    boolean returned = library.returnBook(this);
                    if (returned) {
                        System.out.println(name + " returned: " + book.getTitle());
                    } else {
                        System.out.println(name + " tried to return a book but none was checked out.");
                    }
                }
            }
        }
    }
}
