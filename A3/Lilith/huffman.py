class Node:
    def __init__(self, num:int, val=None, left=None,right=None):
        self.val = val
        self.num = num
        self.left = left
        self.right = right
        self.parent = None

        if self.val==None:
            self.key = "("+str(self.num)+")"
        else:
            self.key = str(self.num)+"|"+self.val

    def __str__(self):
        if self.val==None:
            return f"{self.num}"
        else:
            return f"{self.val} | {self.num}"
        
    def display(self):
        lines, *_ = self._display_aux()
        for line in lines:
            print(line)

    def _display_aux(self):
        """Returns list of strings, width, height, and horizontal coordinate of the root."""
        # No child.
        if self.right is None and self.left is None:
            line = "%s" % self.key
            width = len(line)
            height = 1
            middle = width // 2
            return [line], width, height, middle
        # Only left child.
        if self.right is None:
            lines, n, p, x = self.left._display_aux()
            s = "%s" % self.key
            u = len(s)
            first_line = (x + 1) * " " + (n - x - 1) * "_" + s
            second_line = x * " " + "/" + (n - x - 1 + u) * " "
            shifted_lines = [line + u * " " for line in lines]
            return [first_line, second_line] + shifted_lines, n + u, p + 2, n + u // 2
        # Only right child.
        if self.left is None:
            lines, n, p, x = self.right._display_aux()
            s = "%s" % self.key
            u = len(s)
            first_line = s + x * "_" + (n - x) * " "
            second_line = (u + x) * " " + "\\" + (n - x - 1) * " "
            shifted_lines = [u * " " + line for line in lines]
            return [first_line, second_line] + shifted_lines, n + u, p + 2, u // 2
        # Two children.
        left, n, p, x = self.left._display_aux()
        right, m, q, y = self.right._display_aux()
        s = "%s" % self.key
        u = len(s)
        first_line = (x + 1) * " " + (n - x - 1) * "_" + s + y * "_" + (m - y) * " "
        second_line = (
            x * " " + "/" + (n - x - 1 + u + y) * " " + "\\" + (m - y - 1) * " "
        )
        if p < q:
            left += [n * " "] * (q - p)
        elif q < p:
            right += [m * " "] * (p - q)
        zipped_lines = zip(left, right)
        lines = [first_line, second_line] + [a + u * " " + b for a, b in zipped_lines]
        return lines, n + m + u, max(p, q) + 2, n + u // 2

def huffman_dictionary(root: Node)->dict:
    code_string = ""
    codes = {}
    node = root

    while root.right or root.left:
        while node.right or node.left:
            if node.left:
                node = node.left
                code_string+="0"
            else:
                node = node.right
                code_string+="1"

        if node.val:
            codes[node.val]=code_string


        node = node.parent
        if code_string[len(code_string)-1]=="0":
            node.left=None
        elif code_string[len(code_string)-1]=="1":
            node.right=None

        code_string = code_string[:len(code_string)-1]


    return codes
        
        
def sentence_using_dictionary(sentence:str,dictionary:dict)->str:
    sentence_in_code=""
    for char in sentence:
        sentence_in_code+=dictionary[char]
    
    return sentence_in_code

def getIndexOfTuple(l, index, value):
    for pos,t in enumerate(l):
        if t[index] == value:
            return pos
    return None
        
def decode_huffman(coded:str,dictionary:dict)->str:
    decoded = ""
    code = ""
    tuple_dict = [(k, v) for k, v in dictionary.items()]
    for i in coded:
        code+=i
        index = getIndexOfTuple(tuple_dict,1,code)
        if index:
            char = tuple_dict[index][0]
            decoded+=char
            code=""
    
    return decoded






def huffman_tree(char_nodes:list[int])->Node:
    nodes = sorted(char_nodes,key = lambda x:x.num)
    while len(nodes)>1:
        node1 = nodes.pop(0)
        node2 = nodes.pop(0)

        parent = Node(node1.num+node2.num)
        parent.left = node1
        parent.right = node2
        node1.parent = parent
        node2.parent = parent

        nodes.append(parent)
        nodes = sorted(nodes,key = lambda x:x.num)

    return nodes[0]

chars = {'y':1,'p':1,'j':1,'k':1,'q':1,
         'v':1,'m':2,'x':2,'l':2,'c':2,'z':2,'u':3,
         'h':3,'d':3,'f':3,'s':4,'w':5,'n':6,'e':8,
         'i':8,'r':8,'b':8,'g':9,'t':9,'o':12,
         'a':14,',':39,' ':39
         }

nodes = []
sorted_frequency = sorted(chars.items(),key = lambda x:x[1])

for i in sorted_frequency:
    node =  Node(i[1],i[0])
    nodes.append(node)


root = huffman_tree(nodes)
root.display()

dictionary = huffman_dictionary(root)

for i in dictionary:
    print(i,dictionary[i])

sentence = "YOU GOT TO HAVE FUN BUT YOU NEED TO KNOW HOW SAID THE CAT IN THE HAT"
sentence = sentence.lower()

huffman_sentence = sentence_using_dictionary(sentence,dictionary)
print(huffman_sentence)

print(decode_huffman(huffman_sentence,dictionary))



