import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

class User {
    private String name;
    private int userId;

    public User(String name, int userId) {
        this.name = name;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public int getUserId() {
        return userId;
    }
}

abstract class LibraryItem {
    private String title;
    private int itemId;
    private boolean available;
    private User borrower;

    public LibraryItem(String title, int itemId) {
        this.title = title;
        this.itemId = itemId;
        this.available = true;
        this.borrower = null;
    }

    public String getTitle() {
        return title;
    }

    public int getItemId() {
        return itemId;
    }

    public boolean isAvailable() {
        return available;
    }

    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public abstract void displayDetails();
}

class Book extends LibraryItem {
    private String author;

    public Book(String title, int itemId, String author) {
        super(title, itemId);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public void displayDetails() {
        System.out.println("Book Title: " + getTitle());
        System.out.println("Item ID: " + getItemId());
        System.out.println("Author: " + author);
        System.out.println("Available: " + (isAvailable() ? "Yes" : "No"));
        if (!isAvailable()) {
            System.out.println("Borrower: " + getBorrower().getName() + " (User ID: " + getBorrower().getUserId() + ")");
        }
    }
}

class Library {
    private List<LibraryItem> items;
    private List<User> users;

    public Library() {
        items = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void addItem(LibraryItem item) {
        items.add(item);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void displayLibraryItems() {
        for (LibraryItem item : items) {
            item.displayDetails();
        }
    }

    public LibraryItem getItemById(int itemId) {
        for (LibraryItem item : items) {
            if (item.getItemId() == itemId) {
                return item;
            }
        }
        return null;
    }

    public User getUserById(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    public void addNewUser(String name, int userId) {
        User newUser = new User(name, userId);
        users.add(newUser);
        System.out.println("User added successfully: User ID - " + newUser.getUserId() + ", Name - " + newUser.getName());
    }

    public void displayUsers() {
        System.out.println("List of Users:");
        for (User user : users) {
            System.out.println("User ID: " + user.getUserId() + ", Name: " + user.getName());
        }
    }

    public void addBook(String title, String author, int itemId) {
        LibraryItem book = new Book(title, itemId, author);
        items.add(book);
        System.out.println("Book added successfully: " + book.getTitle());
    }

    public void borrowItem(int itemId, int userId) {
        LibraryItem item = getItemById(itemId);
        User user = getUserById(userId);
        if (item != null && user != null && item.isAvailable()) {
            item.setAvailable(false);
            item.setBorrower(user);
            System.out.println("Book borrowed successfully: " + item.getTitle());
        } else {
            System.out.println("Book not available for borrowing or user does not exist .");
        }
    }

    public void returnItem(int itemId) {
        LibraryItem item = getItemById(itemId);
        if (item != null && !item.isAvailable()) {
            item.setAvailable(true);
            item.setBorrower(null);
            System.out.println("Book returned successfully: " + item.getTitle());
        } else {
            System.out.println("Invalid book ID or book is already available.");
        }
    }

    public void showBorrowedBooks() {
        System.out.println("Borrowed Books:");
        for (LibraryItem item : items) {
            if (!item.isAvailable()) {
                item.displayDetails();
            }
        }
    }

    public void printBorrowedBooksToFile() {
        try (FileWriter writer = new FileWriter("borrowed_books.txt")) {
            for (LibraryItem item : items) {
                if (!item.isAvailable()) {
                    writer.write("Book Title: " + item.getTitle() + ", Item ID: " + item.getItemId() +
                            ", Borrower: " + item.getBorrower().getName() + " (User ID: " +
                            item.getBorrower().getUserId() + ")\n");
                }
            }
            System.out.println("Borrowed books data written to borrowed_books.txt");
        }
        catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public void printUsersToFile() {
        try (FileWriter writer = new FileWriter("users.txt")) {
            for (User user : users) {
                writer.write("User ID: " + user.getUserId() + ", Name: " + user.getName() + "\n");
            }
            System.out.println("Users data written to users.txt");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public void printAllBooksToFile() {
        try (FileWriter writer = new FileWriter("all_books.txt")) {
            for (LibraryItem item : items) {
                writer.write("Book Title: " + item.getTitle() + ", Item ID: " + item.getItemId() +
                        ", Author: " + ((item instanceof Book) ? ((Book) item).getAuthor() : "") + "\n");
            }
            System.out.println("All books data written to all_books.txt");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

}

class LibraryException extends Exception {
    public LibraryException(String message) {
        super(message);
    }
}

public class LibraryManager {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        // Sample data
        library.addItem(new Book("Book1", 1, "Author1"));
        library.addItem(new Book("Book2", 2, "Author2"));
        library.addUser(new User("User1", 1));
        library.addUser(new User("User2", 2));

        while (true) {
            System.out.println("1. Display Library Items");
            System.out.println("2. Add Book");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Show Borrowed Books");
            System.out.println("6. Display Users");
            System.out.println("7. Add User");
            System.out.println("8. Generate Borrowed Books Report");
            System.out.println("9. Generate Users Report");
            System.out.println("10. Generate All Books Report");
            System.out.println("11. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("-------------------------------------------\n");
                    library.displayLibraryItems();
                    System.out.print("-------------------------------------------\n");
                    break;
                case 2:
                    System.out.print("-------------------------------------------\n");
                    System.out.print("Enter book title: ");
                    String bookTitle = scanner.next();
                    System.out.print("Enter author: ");
                    String author = scanner.next();
                    System.out.print("Enter item ID: ");
                    int itemId = scanner.nextInt();
                    library.addBook(bookTitle, author, itemId);
                    System.out.print("-------------------------------------------\n");
                    break;
                case 3:
                    System.out.print("-------------------------------------------\n");
                    System.out.print("Enter book ID to borrow: ");
                    int borrowItemId = scanner.nextInt();
                    System.out.print("Enter user ID: ");
                    int userId = scanner.nextInt();
                    library.borrowItem(borrowItemId, userId);
                    System.out.print("-------------------------------------------\n");
                    break;
                case 4:
                    System.out.print("-------------------------------------------\n");
                    System.out.print("Enter book ID to return: ");
                    int returnItemId = scanner.nextInt();
                    library.returnItem(returnItemId);
                    System.out.print("-------------------------------------------\n");
                    break;
                case 5:
                    System.out.print("-------------------------------------------\n");
                    library.showBorrowedBooks();
                    System.out.print("-------------------------------------------\n");
                    break;
                case 6:
                    System.out.print("-------------------------------------------\n");
                    library.displayUsers();
                    System.out.print("-------------------------------------------\n");
                    break;
                case 7:
                    System.out.print("-------------------------------------------\n");
                    System.out.print("Enter user name: ");
                    String userName = scanner.next();
                    System.out.print("Enter user ID: ");
                    userId = scanner.nextInt();
                    library.addNewUser(userName, userId);
                    System.out.print("-------------------------------------------\n");
                    break;
                case 8:
                    System.out.print("-------------------------------------------\n");
                    library.printBorrowedBooksToFile();
                    System.out.print("-------------------------------------------\n");
                    break;
                case 9:
                    System.out.print("-------------------------------------------\n");
                    library.printUsersToFile();
                    System.out.print("-------------------------------------------\n");
                    break;
                case 10:
                    System.out.print("-------------------------------------------\n");
                    library.printAllBooksToFile();
                    System.out.print("-------------------------------------------\n");
                    break;
                case 11:
                    System.out.print("-------------------------------------------\n");
                    System.out.println("Exiting the system");
                    System.out.print("-------------------------------------------\n");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
