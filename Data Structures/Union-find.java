public class UnionFind {
    private int[] parent;  // Array to store parent of each node
    private int[] rank;    // Array to store rank (tree height) of each node
    
    // Constructor initializes the Union-Find structure
    public UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];
        
        // Initially, each node is its own parent (self-loop)
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;  // Initial rank is 0 for all nodes
        }
    }
    
    /**
     * Finds the root of the given element 'x' with path compression.
     * Path compression ensures that all nodes in the path directly point to the root,
     * making future searches faster.
     * 
     * @param x the element whose root we want to find
     * @return the root of the set containing 'x'
     */
    public int find(int x) {
        // If x is not its own parent, recursively find its root and compress the path
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // Path compression
        }
        return parent[x];
    }
    
    /**
     * Merges the sets containing elements 'x' and 'y' using union by rank.
     * Union by rank attaches the smaller tree under the larger tree to keep the structure balanced.
     * 
     * @param x an element in the first set
     * @param y an element in the second set
     * @return true if union was successful (they were in different sets), false if already connected
     */
    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        
        // If they are already in the same set, no union is needed
        if (rootX == rootY) {
            return false;
        }
        
        // Union by rank to keep the tree balanced
        if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++; // Increment rank if both have the same rank
        }
        return true;
    }
    
    /**
     * Checks if elements 'x' and 'y' are in the same set.
     * 
     * @param x an element in the first set
     * @param y an element in the second set
     * @return true if both elements are in the same set, false otherwise
     */
    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
    
    public static void main(String[] args) {
        // Sample usage of UnionFind
        int n = 10; // Number of elements
        UnionFind uf = new UnionFind(n);
        
        uf.union(1, 2);
        uf.union(2, 3);
        uf.union(4, 5);
        
        System.out.println("Is 1 connected to 3? " + uf.connected(1, 3)); // true
        System.out.println("Is 1 connected to 5? " + uf.connected(1, 5)); // false
        
        uf.union(3, 4); // Now 1 and 5 are connected indirectly
        
        System.out.println("Is 1 connected to 5? " + uf.connected(1, 5)); // true
    }
}
