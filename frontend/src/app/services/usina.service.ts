import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RankingPotencia } from '../models/ranking.model';

@Injectable({ providedIn: 'root' })
export class UsinaService {
  private readonly API = 'http://localhost:8080/api/usinas/ranking-potencia';

  constructor(private http: HttpClient) { }

  getRanking(): Observable<RankingPotencia[]> {
    return this.http.get<RankingPotencia[]>(this.API);
  }
}
