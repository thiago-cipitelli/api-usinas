import { Component, Input } from '@angular/core';

@Component({
    selector: 'app-stats-card',
    standalone: true,
    templateUrl: './stats-card.html',
    styleUrl: './stats-card.css'
})
export class StatsCardComponent {
    @Input() icon = '📊';
    @Input() value = '0';
    @Input() label = 'Estatística';
    @Input() color: 'primary' | 'success' | 'warning' | 'info' = 'primary';
}
