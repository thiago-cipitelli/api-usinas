import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-ranking',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ranking.html'
})
export class RankingComponent {
  private http = inject(HttpClient);

  ranking$: Observable<any[]> = this.http.get<any[]>('http://localhost:8080/usinas/ranking-potencia');
}
