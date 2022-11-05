public class SortedList implements SortedListInterface {
    private static final int MAX_LIST = 50;
    private Object items[];  // an array of list items
    private int numItems;    // number of items in list

    // default constructor
    public SortedList() {
         items = new Object[MAX_LIST];
         numItems = 0;
    }

    public boolean isEmpty() {
        return numItems == 0;
    }

    public int size() {
        return numItems;
    }

    public Object get(int index) throws ListException {

        if (index >= 1 && index <= size()) {
            return items[index-1];
        } else  {  // index out of range
            throw new ListException("get (index out of range): " + index);
        }
    }

    public void removeAll() {
        items = new Object[MAX_LIST];
        numItems = 0;
    }

    // new operations: sortedAdd
    public void sortedAdd(Comparable newItem) throws ListException {
        int index = 1;

        if (size() == MAX_LIST)
            throw new ListException("add (array is full)");

        // ToDo
        // Create a new array with size MAX_LIST to store the new item.
        Object[] newItems = new Object[MAX_LIST];

        // Get the index of where the item should be located.
        index = locateIndex(newItem);

        // Loop through every valid index in newItems, which is one greater than the valid indices in items.
        for(int i = 1; i <= numItems + 1; i++) {
            // Check if the current index is before the index of the item to be added.
            if(i < index) {
                // Add the current item to newItems in the same position.
                newItems[i - 1] = get(i);
            }
            
            // Otherwise, check if the current index is equal to the index of the item to be added.
            else if(i == index) {
                // Add the new item to newItems in the current position.
                newItems[i - 1] = newItem;
            }

            // Otherwise, the current index in items must be added after the new item.
            else {
                // Add to newItems the value of items[i - 1], since the newItem is now in the list.
                newItems[i - 1] = get(i - 1);
            }
        }

        // Re-initialze items as an array with the same size as newItems.
        items = new Object[newItems.length];

        // Loop through every index in items, and create a hard-copy of newItems.
        for(int i = 0; i < items.length; i++) {
            // Set the value of items[i] to its corresponding value in newItems[].
            items[i] = newItems[i];
        }

        // Increment the numItems count.
        numItems++;
    }

    // new operation: sortedRemove
    public void sortedRemove(Comparable anItem) throws ListException {
        int index = 1;

        // ToDo
        // Create a new array with size MAX_LIST to store the smaller list.
        Object[] newItems = new Object[MAX_LIST];

        // Get the index of where the item is located.
        index = locateIndex(anItem);

        // Loop through every valid index in newItems, which is one less than the valid indices in items.
        for(int i = 1; i <= numItems - 1; i++) {
            // Check if the current index is before the index of the item to be removed.
            if(i < index) {
                // Add the current item to newItems in the same position.
                newItems[i - 1] = get(i);
            }
            
            // Otherwise, check if the current index is equal to the index of the item to be removed.
            else if(i == index) {
                // Check if the current item at the index where the item to be removed is located is not equivalent to the item to be removed.
                if(anItem.compareTo(get(i)) != 0) {
                    // The item to be removed does not exist within the list. Throw a ListException.
                    throw new ListException("sortedRemove (item not in the list): " + anItem);
                }

                // Do not add the current item to the new list. Instead, add the next index of items to newItems.
                newItems[i - 1] = get(i + 1);
            }

            // Otherwise, the current index in items must be after the removed item.
            else {
                // Add to newItems the value of items[i + 1], since the newItem is not in the new list.
                newItems[i - 1] = get(i + 1);
            }
        }

        // Re-initialze items as an array with the same size as newItems.
        items = new Object[newItems.length];

        // Loop through every index in items, and create a hard-copy of newItems.
        for(int i = 0; i < items.length; i++) {
            // Set the value of items[i] to its corresponding value in newItems[].
            items[i] = newItems[i];
        }

        // Decrement the numItems count.
        numItems--;
    }

    // new operation: locateIndex
    public int locateIndex(Comparable anItem) {
        // Set the default index to numItems + 1, since if item should be added to the end, the index is correct.
        int index = numItems + 1;

        // ToDo
        // Loop through every valid index in items.
        for(int i = 1; i <= numItems; i++) {
            try {
                // Check if anItem is equivalent to the current value in items, or belongs before the current value in items.
                if(anItem.compareTo(get(i)) == 0 || anItem.compareTo(get(i)) < 0) {
                    // The current item is the item that is being located, or should be located at this index.
                    index = i;

                    // Break out of the loop, since the index has been found.
                    break;
                }
            } catch(ListException e) {}
        }

        // Return the index.
        return index;
    }
}