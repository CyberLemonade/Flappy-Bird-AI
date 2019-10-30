class NeuralNetwork {
    final int layers[];
    final double mutation_rate = 0.05;

    Matrix[] weights;

    Matrix[] values;

    NeuralNetwork(int[] layers) {
        this.layers = layers.clone();
        this.weights = new Matrix[layers.length-1];
        for (int i = 0; i < layers.length - 1; i++) {
            weights[i] = new Matrix(layers[i+1],layers[i]+1,2.0);
        }
    }

    @Override
    public NeuralNetwork clone() {
        NeuralNetwork clone = new NeuralNetwork(this.layers);
        for (int i = 0; i < this.layers.length - 1; i++) {
            clone.weights[i] = new Matrix(this.weights[i]);
        }
        return clone;
    }

    void debug() {
        for (int i = 0; i < layers.length - 1; i++) {
            System.err.println("weights["+i+"] = ");
            weights[i].debug();
        }
    }

    double[] guess(double[] inputs) {
        this.values = new Matrix[layers.length];
        this.values[0] = new Matrix(inputs,true);
        feedForward();
        return values[layers.length-1].toArray();
    }

    void feedForward() {
        for (int i = 0; i < values.length - 1; i++) {
            values[i].pushRow();
            values[i+1] = Matrix.dotProduct(weights[i],values[i]);
            values[i+1].sigmoid();
        }
    }

    void mutate() {
        final double amplitude = 0.3;
        for(int i = 0; i < this.weights.length; i++) {
            for (int y = 0; y < weights[i].r; y++) {
                for (int x = 0; x < weights[i].c; x++) {
                    if (Math.random() < mutation_rate) {
                        weights[i].m[y][x] += Math.random()*amplitude - amplitude/2.0;
                        if (weights[i].m[y][x] > 1.0) weights[i].m[y][x] = 1.0;
                        else if (weights[i].m[y][x] < -1.0) weights[i].m[y][x] = -1.0;
                    }
                }
            }
        }
    }

    void crossover(NeuralNetwork p) {
        for(int i = 0; i < this.weights.length; i++) {
            for (int y = 0; y < weights[i].r; y++) {
                for (int x = 0; x < weights[i].c; x++) {
                    weights[i].m[y][x] = (Math.random() < 0.5) ? weights[i].m[y][x] : p.weights[i].m[y][x];
                }
            }
        }
    }
}