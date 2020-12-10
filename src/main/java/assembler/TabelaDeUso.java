package assembler;

    public class TabelaDeUso {
        private String simbolo,relocabilidade,operacao;
        private int LocationCounter;

        public String getSimbolo() {
            return simbolo;
        }

        public void setSimbolo(String simbolo) {
            this.simbolo = simbolo;
        }

        public String getRelocabilidade() {
            return relocabilidade;
        }

        public void setRelocabilidade(String relocabilidade) {
            this.relocabilidade = relocabilidade;
        }

        public String getOperacao() {
            return operacao;
        }

        public void setOperacao(String operacao) {
            this.operacao = operacao;
        }

        public int getLocationCounter() {
            return LocationCounter;
        }

        public void setLocationCounter(int LocationCounter) {
            this.LocationCounter = LocationCounter;
        }


    }

