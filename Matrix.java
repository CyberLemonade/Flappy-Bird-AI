class Matrix {
    int r, c;
    double[][] m;

    // CONSTRUCTORS:

    Matrix(int r,int c) {
        this.r = r;
        this.c = c;
        this.m = new double[r][c];
    }

    Matrix(int r,int c,double range) {
        this.r = r;
        this.c = c;
        this.m = new double[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                m[i][j] = Math.random()*range - range/2.0;
            }
        }
    }

    Matrix(double[][] parent) {
        this.r = parent.length;
        this.c = parent[0].length;
        this.m = new double[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                m[i][j] = parent[i][j];
            }
        }
    }

    Matrix(Matrix parent) {
        this.r = parent.r;
        this.c = parent.c;
        this.m = new double[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                m[i][j] = parent.m[i][j];
            }
        }
    }

    Matrix(double[] arr, boolean collumn) {
        if (collumn) {
            this.r = arr.length;
            this.c = 1;
            this.m = new double[r][c];
            for (int i = 0; i < r; i++) {
                m[i][0] = arr[i];
            }
        } else {
            this.r = 1;
            this.c = arr.length;
            this.m = new double[r][c];
            m[0] = arr.clone();
        }
    }

    // NON - STATIC FUNCTIONS :

    void randomise(double range) {
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                m[i][j] = Math.random()*range - range/2.0;
            }
        }
    }

    void pushRow() {
        r++;
        double[][] dummy = new double[r][c];
        for (int i = 0; i < r-1; i++) {
            for (int j = 0; j < c; j++) {
                dummy[i][j] = m[i][j];
            }
        }
        for (int i = 0; i < c; i++) {
            dummy[r-1][i] = 1.0;
        }
        m = dummy;
    }

    void pushCollumn() {
        c++;
        double[][] dummy = new double[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c-1; j++) {
                dummy[i][j] = m[i][j];
            }
        }
        for (int i = 0; i < r; i++) {
            dummy[i][c-1] = 1.0;
        }
        m = dummy;
    }

    void removeRow() {
        r--;
        double[][] dummy = new double[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                dummy[i][j] = m[i][j];
            }
        }
        m = dummy;
    }

    void removeCollumn() {
        c--;
        double[][] dummy = new double[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                dummy[i][j] = m[i][j];
            }
        }
        m = dummy;
    }
    
    double[] toArray() {
        if (r == 1) {
            return m[0].clone();
        } else if (c == 1) {
            double[] arr = new double[r];
            for (int i = 0; i < r; i++) {
                arr[i] = m[i][0];
            }
            return arr;
        } else {
            return null;
        }
    }

    Matrix dotProduct(Matrix b) {
        Matrix a = this;
        if (a.c != b.r) {
            return null;
        } else {
            Matrix c = new Matrix(a.r, b.c);
            for (int i = 0; i < c.r; i++) {
                for (int j = 0; j < c.c; j++) {
                    double sum = 0;
                    for (int k = 0; k < this.c; k++) {
                        sum += a.m[i][k] * b.m[k][j];
                    }
                    c.m[i][j] = sum;
                }
            }
            return c;
        }
    }

    void add(Matrix b) {
        if (r == b.r && c == b.c) {
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    m[i][j] += b.m[i][j];
                }
            }
        }
    }

    void add(double val) {
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                m[i][j] += val;
            }
        }
    }

    void multiply(Matrix b) {
        if (r == b.r && c == b.c) {
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    m[i][j] *= b.m[i][j];
                }
            }
        }
    }

    void multiply(double val) {
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                m[i][j] *= val;
            }
        }
    }

    Matrix transpose() {
        Matrix b = new Matrix(c,r);
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                b.m[j][i] = m[i][j];
            }
        }
        return b;
    }

    void debug() {
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                System.err.print(m[i][j]+" ");
            }
            System.err.println();
        }
    }

    void sigmoid() {
        // s(x) = 1/1+e^-x
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                m[i][j] = 1.0 / (1.0 + Math.exp(-m[i][j]));
            }
        }
    }

    void dsigmoid() {
        // s'(x) = s(x)*(1-s(x))
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                m[i][j] = m[i][j] * (1 - m[i][j]);
            }
        }
    }

    // STATIC FUNCTIONS :

    static Matrix dotProduct(Matrix a,Matrix b) {
        if (a.c != b.r) {
            return null;
        } else {
            Matrix c = new Matrix(a.r, b.c);
            for (int i = 0; i < c.r; i++) {
                for (int j = 0; j < c.c; j++) {
                    double sum = 0;
                    for (int k = 0; k < a.c; k++) {
                        sum += a.m[i][k] * b.m[k][j];
                    }
                    c.m[i][j] = sum;
                }
            }
            return c;
        }
    }

    static Matrix add(Matrix a,Matrix b) {
        Matrix c = new Matrix(a);
        if (a.r == b.r && a.c == b.c) {
            for (int i = 0; i < c.r; i++) {
                for (int j = 0; j < c.c; j++) {
                    c.m[i][j] = a.m[i][j] + b.m[i][j];
                }
            }
            return c;
        } else {
            return null;
        }
    }

    static Matrix subtract(Matrix a,Matrix b) {
        Matrix c = new Matrix(a);
        if (a.r == b.r && a.c == b.c) {
            for (int i = 0; i < c.r; i++) {
                for (int j = 0; j < c.c; j++) {
                    c.m[i][j] = a.m[i][j] - b.m[i][j];
                }
            }
            return c;
        } else {
            return null;
        }
    }

    static Matrix multiply(Matrix a,Matrix b) {
        Matrix c = new Matrix(a);
        if (a.r == b.r && a.c == b.c) {
            for (int i = 0; i < c.r; i++) {
                for (int j = 0; j < c.c; j++) {
                    c.m[i][j] = a.m[i][j] * b.m[i][j];
                }
            }
            return c;
        } else {
            return null;
        }
    }

    static Matrix transpose(Matrix a) {
        Matrix b = new Matrix(a.c,a.r);
        for (int i = 0; i < a.r; i++) {
            for (int j = 0; j < a.c; j++) {
                b.m[j][i] = a.m[i][j];
            }
        }
        return b;
    }

    static double[] toArray(Matrix a) {
        if (a.r == 1) {
            return a.m[0].clone();
        } else if (a.c == 1) {
            double[] arr = new double[a.r];
            for (int i = 0; i < a.r; i++) {
                arr[i] = a.m[i][0];
            }
            return arr;
        } else {
            return null;
        }
    }

    static Matrix sigmoid(Matrix b) {
        Matrix a = new Matrix(b);
        // s(x) = 1/1+e^-x
        for (int i = 0; i < a.r; i++) {
            for (int j = 0; j < a.c; j++) {
                a.m[i][j] = 1.0 / (1.0 + Math.exp(-a.m[i][j]));
            }
        }
        return a;
    }

    static Matrix dsigmoid(Matrix b) {
        Matrix a = new Matrix(b);
        // s'(x) = s(x)*(1-s(x))
        for (int i = 0; i < a.r; i++) {
            for (int j = 0; j < a.c; j++) {
                a.m[i][j] = a.m[i][j] * (1 - a.m[i][j]);
            }
        }
        return a;
    }
}