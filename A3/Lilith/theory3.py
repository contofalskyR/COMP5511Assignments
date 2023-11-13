alphabet = "YOU, ME, WE, SHE, HE, SOW, COW, DOG, PIG, RIG, GOLD, SEA, RUG, HAT, CAT, ROW, MOB, LOG, BOX, TAB, BAR, EAR, TAR, JAR, DIG, FAN, BIG, TEA, NOW, FOX, BOG, BAT, BIT, KIT, SIT, ZEN, RAN, FAN, QUIZ, VAN"
frequency={}

for char in alphabet:
    if char not in frequency:
        frequency[char]=1
    else:
        frequency[char]+=1

chars_by_frequency = sorted(frequency.items(),key = lambda x:x[1])

print(chars_by_frequency)

print(len(alphabet))
