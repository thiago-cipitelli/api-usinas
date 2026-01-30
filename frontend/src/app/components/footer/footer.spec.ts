import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FooterComponent } from './footer';

describe('FooterComponent', () => {
    let component: FooterComponent;
    let fixture: ComponentFixture<FooterComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [FooterComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(FooterComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should show current year', () => {
        const currentYear = new Date().getFullYear().toString();
        const compiled = fixture.nativeElement as HTMLElement;
        expect(compiled.querySelector('.footer-text')?.textContent).toContain(currentYear);
    });
});
