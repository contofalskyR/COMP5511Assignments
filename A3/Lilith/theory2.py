def h1(i: int)->int:
    return(2*i+5)%17

def h2(i: int)->int:
    return(7-i%7)+1

def double_hash(key: int, iteration: int)->int:
    return (h1(key)+iteration*h2(key))%17

initial_values = [12,44,13,17,24,88,23,94,11,39,34,20,16,51]
results_of_hash = {}

for val in initial_values:
    results_of_hash[val]=h1(val)

print(results_of_hash)

print("34 goes in:", results_of_hash[34])
print("double hash 34:",double_hash(34,1))

print("20 goes in:", results_of_hash[20])
print("double hash 20:",double_hash(20,1))

print("51 goes in:", results_of_hash[51])
print("double hash 51:",double_hash(51,1))
print("double hash 51:",double_hash(51,2))
print("double hash 51:",double_hash(51,3))
print("double hash 51:",double_hash(51,4))
print("double hash 51:",double_hash(51,5))

