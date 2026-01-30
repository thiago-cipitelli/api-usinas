export interface Usina {
  ceg: string;
  nome: string;
  potencia: number;
  dataRalie: string;
}

// Alias para compatibilidade
export type RankingPotencia = Usina;
