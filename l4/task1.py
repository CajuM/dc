#!/usr/bin/env python

import sys

import numpy as np

from matplotlib import pyplot as plt
from scipy.special import zeta

def main(f):
    with open(f) as f:
        data = [line.split() for line in f]

    data = [(line[0], int(line[1])) for line in data]
    data.sort(key=lambda row: -row[1])

    x = np.arange(1, len(data) + 1)
    y = np.array([row[1] for row in data])
    z = np.sum(y) * (x ** -1.25) / zeta(x)

    plt.yscale('log')
    plt.plot(x, y)
    plt.plot(x, z)
    plt.show()

if __name__ == '__main__':
    main(sys.argv[1])
