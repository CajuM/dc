#!/usr/bin/env python

import sys

import numpy as np

from matplotlib import pyplot as plt


def main(f):
    with open(f) as f:
        data = [line.split() for line in f]

    ncache = np.array([int(line[2]) for line in data if line[0] == 'TIME:'])
    cache = np.array([int(line[2]) for line in data if line[0] == 'TIME-cached:'])

    plt.plot(ncache, label='Without cache')
    plt.plot(cache, label='With cache')
    plt.ylabel('ns')
    plt.xlabel('vowel')
    plt.legend()
    plt.show()

if __name__ == '__main__':
    main(sys.argv[1])
