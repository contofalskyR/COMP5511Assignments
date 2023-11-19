
/**
 * The mergingTrees function combines two binary trees (T1 and T2), each maintaining the heap-order property,
 * into a single binary tree T'. It achieves this by ensuring the root of T' is the smaller root of T1 or T2.
 * The function then recursively merges the right subtree of the tree with the smaller root with T2, applying
 * a heapify process to maintain a heap-order property throughout the tree. This process is conducted in a 
 * bottom-up manner, affecting only those nodes along the merge path, thus preserving the heap-order without
 * full tree traversal. The process ensures the combined tree T' maintains 
 * the heap-order property and aligns with the O(h1 + h2) time complexity goal by focusing only on necessary nodes
 * and avoiding redundant operations.
 */




// // Function to merge two binary trees with heap-order property
 
// Function mergingTrees(T1, T2):
//     // Checking if trees are empty
//     If T1 is empty: 
//         Return T2
//     If T2 is empty:
//         Return T1

//     // Confirming that T1 has the smallest root
//     If root(T1) > root(T2):
//         Swap T1 with T2

//     // Merging T2 into T1 while maintaining heap order property using recursive calls
//     // Assigning the smallest root as the root of the merged tree
//     mergedRoot = new Node(root(T1))
//     mergedRoot.leftChild = T1.leftChild
//     mergedRoot.rightChild = mergeWithHeapify(T1.rightChild, T2)
    
//     Return mergedRoot

// // Function to merge the right child of T1 and T2 with a heapify process
// Function mergeWithHeapify(T1_rightChild, T2):
//     // Perform the merging process ensuring it's bottom-up
//     If T1_rightChild is null:
//         Return heapify(T2)
//     If T2 is null:
//         Return heapify(T1_rightChild)

//     If root(T1_rightChild) > root(T2):
//         Swap T1_rightChild with T2

//     mergedRoot = new Node(root(T1_rightChild))
//     mergedRoot.leftChild = T1_rightChild.leftChild
//     mergedRoot.rightChild = mergeWithHeapify(T1_rightChild.rightChild, T2)
//     mergedRoot = heapify(mergedRoot)
    
//     Return mergedRoot

// // Function to ensure the heap property in a subtree, bottom-up

// Function heapify(node):
//     If node is null:
//         return null

//     // checking the heap-order property and adjust if necessary
//     If not heapOrdered(node):
//         smallest = findSmallestChild(node)
//         If smallest != null:
//             Swap node with smallest
//             heapify(node) // Continue heapifying at the subtree of the smallest

//     Return node

// // Function to find the smallest child of a node
// Function findSmallestChild(node):
//     smallest = node
//     If node.leftChild is present and node.leftChild < smallest: 
//         smallest = node.leftChild
//     If node.rightChild is present and node.rightChild < smallest: 
//         smallest = node.rightChild
//     Return smallest

// // Function to check if a node and its descendants maintain heap order
// Function heapOrdered(node): 
//     If node is null:
//         return True

//     // Confirm the heap-order property for the left and right children
//     isLeftHeapOrdered = (node.leftChild is null) or (node.value <= node.leftChild.value and heapOrdered(node.leftChild))
//     isRightHeapOrdered = (node.rightChild is null) or (node.value <= node.rightChild.value and heapOrdered(node.rightChild))
    
//     Return isLeftHeapOrdered and isRightHeapOrdered
