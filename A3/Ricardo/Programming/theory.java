
// Theory 
// Q1) 
// Function mergingTrees(T1, T2):
//     // Checking if trees are empty
//     If T1 is empty: 
//         Return T2
//     If T2 is empty:
//         Return T1

//     // Confirming that T1 has the smallest root 
//     If root(T1) > root(T2):
//         Swap T1 with T2

//     // Merging T2 and T1 but still maintaining the heap order property using recursive calls
//     // Assigning the smallest root as the root of the merged tree
//     mergedRoot = root(T1)
//     mergedRoot.leftChild = mergingTrees(leftChild(T1), T2)
//     If not heapOrdered(mergedRoot):
//         heapify(mergedRoot)
//     Return mergedRoot

// Function heapify(node): 
//     smallest = node
//     If node.leftChild is present and node.leftChild < smallest: 
//         smallest = node.leftChild
//     If node.rightChild is present and node.rightChild < smallest: 
//         smallest = node.rightChild

//     If smallest != node: 
//         Swap node with smallest 
//         heapify(smallest) // Continue heapifying at the subtree of the smallest

// Function heapOrdered(node): 
//     If node is null:
//         return True			

//     // Check the heap-order property for the left child
//     isLeftHeapOrdered = (node.leftChild is null) or (node.value <= node.leftChild.value and heapOrdered(node.leftChild))

//     // Check the heap-order property for the right child
//     isRightHeapOrdered = (node.rightChild is null) or (node.value <= node.rightChild.value and heapOrdered(node.rightChild))
//     Return isLeftHeapOrdered and isRightHeapOrdered


