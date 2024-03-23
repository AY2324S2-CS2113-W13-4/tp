package bookmarked.command;

import bookmarked.Book;
import bookmarked.exceptions.emptyListException;
import bookmarked.ui.Ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BorrowCommand extends Command {
    String bookName;
    ArrayList<Book> listOfBooks;
    public BorrowCommand(String[] commandParts, ArrayList<Book> listOfBooks) {
        assert commandParts != null : "commandParts should not be null";
        assert commandParts.length > 1 : "commandParts should contain at least two elements";
        this.bookName = String.join(" ", List.of(commandParts).subList(1, commandParts.length));
        assert listOfBooks != null : "listOfBooks should not be null";
        this.listOfBooks = listOfBooks;
    }

    @Override
    public void handleCommand() {
        // Find the book with the matching name
        List<Book> foundBooks = listOfBooks.stream()
                .filter(book -> book.getName().equalsIgnoreCase(bookName))
                .collect(Collectors.toList());
        try {
            runBorrowCommand(foundBooks);
        } catch (emptyListException e) {
            Ui.printEmptyListMessage();
        }
    }

    public void runBorrowCommand(List<Book> foundBooks) throws emptyListException {
        if (this.listOfBooks.isEmpty()) {
            throw new emptyListException();
        }

        if (!foundBooks.isEmpty()) {
            // Assuming that there can be multiple books with the same name,
            // and we mark all copies as borrowed
            foundBooks.forEach(book -> {
                //assert !book.isBorrowed() : "Book is already borrowed: " + book.getName();
                book.setBorrowed();
                System.out.println("Borrowed " + book.getName() + "!");
            });
        } else {
            System.out.println("Book not found: " + bookName);
        }
    }
}
