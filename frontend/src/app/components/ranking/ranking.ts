import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RankingPotencia } from '../../models/ranking.model';
import { LoadingSpinnerComponent } from '../loading-spinner/loading-spinner';

@Component({
  selector: 'app-ranking',
  standalone: true,
  imports: [CommonModule, LoadingSpinnerComponent],
  templateUrl: './ranking.html',
  styleUrl: './ranking.css'
})
export class RankingComponent {
  private http = inject(HttpClient);

  ranking$: Observable<RankingPotencia[]> = this.http.get<RankingPotencia[]>('http://localhost:8080/usinas/ranking-potencia');

  getMedalEmoji(index: number): string {
    const medals = ['🥇', '🥈', '🥉'];
    return medals[index] || '';
  }

  getPositionClass(index: number): string {
    if (index === 0) return 'rank-1';
    if (index === 1) return 'rank-2';
    if (index === 2) return 'rank-3';
    return '';
  }
}
