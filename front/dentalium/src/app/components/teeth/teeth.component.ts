import {Component, ElementRef, EventEmitter, OnInit, Output, Renderer2} from '@angular/core';
declare var $: any;  // Declare jQuery to avoid TypeScript errors

@Component({
  selector: 'app-teeth',
  imports: [],
  templateUrl: './teeth.component.html',
  styleUrl: './teeth.component.css'
})
export class TeethComponent {

  selectedTeeth: Set<number> = new Set();

  constructor() { }

  @Output() selectToothEvent = new EventEmitter();
  selectTooth(toothId: number): void {
    const tooth = document.getElementsByClassName(`tooth-${toothId}`);
    for (let i = 0; i < tooth.length; i++) {
      const currentStyle = tooth[i].getAttribute('style') || '';

      let isColour: boolean = currentStyle.includes('fill: red');

      if (isColour) {
        this.selectedTeeth.delete(toothId)
        tooth[i].setAttribute('style', currentStyle.replace('fill: red', 'fill: white'));
      } else {
        this.selectedTeeth.add(toothId)
        tooth[i].setAttribute('style', currentStyle.replace('fill: white', 'fill: red'));
      }
    }
    this.selectToothEvent.emit(Array.from(this.selectedTeeth).join(','));
  }

  cleanTeethImage(): void {
    const allElements = document.querySelectorAll('[class*="tooth-"]');
    const regex = /^tooth-\d+$/;
    const toothElements = Array.from(allElements).filter(el =>
      Array.from(el.classList).some(cls => regex.test(cls))
    );
    for (let i = 0; i < toothElements.length; i++) {
      const currentStyle = toothElements[i].getAttribute('style') || '';
      toothElements[i].setAttribute('style', currentStyle.replace('fill: red', 'fill: white'));
    }

    this.selectedTeeth.clear()

    }

  }
