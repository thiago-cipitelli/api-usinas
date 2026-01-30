import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CardComponent } from './card';

describe('CardComponent', () => {
    let component: CardComponent;
    let fixture: ComponentFixture<CardComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [CardComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(CardComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should apply variant class', () => {
        fixture.componentRef.setInput('variant', 'primary');
        fixture.detectChanges();
        const compiled = fixture.nativeElement as HTMLElement;
        expect(compiled.querySelector('.card')?.classList).toContain('card-primary');
    });
});
