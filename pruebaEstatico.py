
import os, sys

def calcularAreaCirculo ( radio ) :
pi = 3.1416
area = pi * radio **2
 return area

def imprimirResultado(radio):
    resultado = calcularAreaCirculo(radio)
    print("El area del circulo es:",resultado)

def main():
    r = input("Introduce el radio del circulo: ")
    r = float(r)
    imprimirResultado(radio=r)

main()
