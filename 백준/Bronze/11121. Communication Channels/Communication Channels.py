t = int(input())
for _ in range(t):
    a, b = map(str, input().split())
    print("OK" if a == b else "ERROR")