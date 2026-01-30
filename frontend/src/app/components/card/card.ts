import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-card',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './card.html',
    styleUrl: './card.css'
})
export class CardComponent {
    @Input() variant: 'default' | 'primary' | 'success' = 'default';
    @Input() hoverable = true;
}
