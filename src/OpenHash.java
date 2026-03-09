public class OpenHash {
    private static class Entry {

        String key;
        String value;
        boolean deleted;

        Entry(String key, String value) {

            this.key = key;
            this.value = value;
            deleted = false;
        }
    }

    private Entry[] table;
    private int m;
    private int size;

    public OpenHash(int m) {

        this.m = m;
        table = new Entry[m + 1];
        size = 0;
    }

    private int hash(String key) {
        return Math.abs(key.hashCode()) % m + 1;
    }

    public void insert(String key, String value) {

        if (size >= m) return;

        int i = hash(key);
        int start = i;

        while (table[i] != null && !table[i].deleted) {

            if (table[i].key.equals(key)) {

                table[i].value = table[i].value + ", " + value;
                return;
            }

            i = (i % m) + 1;

            if (i == start) return;
        }

        table[i] = new Entry(key, value);
        size++;
    }

    public String lookup(String key) {

        int i = hash(key);
        int start = i;

        while (table[i] != null) {

            if (!table[i].deleted && table[i].key.equals(key))
                return table[i].value;

            i = (i % m) + 1;

            if (i == start) break;
        }

        return null;
    }
}
