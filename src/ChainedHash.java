public class ChainedHash {

    private static class Node {

    String key;
    String value;
    Node next;

    Node(String key, String value) {
        this.key = key;
        this.value = value;
    }
}

    private Node[] table;
    private int m;

    public ChainedHash(int m) {

        this.m = m;
        table = new Node[m + 1];
    }

    private int hash(String key) {
        return Math.abs(key.hashCode()) % m + 1;
    }

    public void insert(String key, String value) {

        int i = hash(key);

        if (table[i] == null) {
            table[i] = new Node(key, value);
            return;
        }

        Node current = table[i];
        Node prev = null;

        while (current != null) {

            if (current.key.equals(key)) {

                current.value = current.value + ", " + value;
                return;
            }

            prev = current;
            current = current.next;
        }

        prev.next = new Node(key, value);
    }

    public String lookup(String key) {

        int i = hash(key);

        Node current = table[i];

        while (current != null) {

            if (current.key.equals(key))
                return current.value;

            current = current.next;
        }

        return null;
    }
}
